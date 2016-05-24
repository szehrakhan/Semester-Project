/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ui;

/**
 *
 * @author MuhammadJawad
 */


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.RandomizableMultipleClassifiersCombiner;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;




import db.entity.Accident;
import db.entity.Info;

import db.util.HibernateUtil;
import db.entity.Post;
import static db.ui.gui.add;
import static db.ui.gui.answer;
import static db.ui.gui.frame;
import static db.ui.gui.frame_add;
import static db.ui.gui.location;
import static db.ui.gui.predict;
import static db.ui.map.getLatLongPositions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//inport db.entity;
public class executioner {
    
     private static SessionFactory factory; 
   
     //static double lati,longi,post;
   static List<Double> lati = new ArrayList<Double>();
   static List<Double> longi = new ArrayList<Double>();
   static List<String> post = new ArrayList<String>();
   static gui g= new gui();
   static String latLongs[];
   static map m=new map();
    
    /* Method to list all the accidents detail */
   public static void listPosts( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List posts = session.createQuery("FROM db.entity.Post").list(); 
         for (Iterator iterator1 = 
                           posts.iterator(); iterator1.hasNext();){
            Post tmp_post = (Post) iterator1.next(); 
            System.out.print("Post Name: " + tmp_post.getPost_name()); 
            System.out.print("  Post Lat: " + tmp_post.getPost_lat()); 
            System.out.println("  Post Lang: " + tmp_post.getPost_long());
            Set infos = tmp_post.getInfos();
            for (Iterator iterator2 = 
                         infos.iterator(); iterator2.hasNext();){
                  Info info = (Info) iterator2.next(); 
                  System.out.println("Info containing that post: " + info.getDistance()); 
            }
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
    
   
   
   
   
   /* Method to list all the accidents detail */
   public static void listAccidents( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List accidents = session.createQuery("FROM db.entity.Accident").list(); 
         for (Iterator iterator1 = 
                           accidents.iterator(); iterator1.hasNext();){
            
             Accident tmp_acc = (Accident) iterator1.next(); 
            System.out.print("Acc location: " + tmp_acc.getAccLoc()); 
            System.out.print("Acc Lat: " + tmp_acc.getAccLat()); 
            System.out.println("Acc Lang: " + tmp_acc.getAccLong());
            
            
            Info info = tmp_acc.getInfo(); 
            System.out.println("Info containing that post: " + info.getDistance()); 
            
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   
   //predictions
   
    public static void Predict( ) throws Exception{
   // System.out.print("hahahah");
        Attribute one=new Attribute("latitude");
        Attribute two=new Attribute("longitude");
       
        //initializing post numbers
        
        FastVector ClassVal = new FastVector(50);
        for(int i=0;i<=50;i++)
        ClassVal.addElement(String.valueOf(i));
        Attribute posts=new Attribute("posts",ClassVal);
        
        FastVector training = new FastVector(3);
        training.addElement(one);
        training.addElement(two);
        training.addElement(posts);
        
        Instances isTrainingSet = new Instances("ambulance_posts", training, 2);
        isTrainingSet.setClassIndex(2);
        
        
        for(int i=0;i<post.size();i++)
        {
        
        Instance j = new Instance(3);
 j.setValue((Attribute)training.elementAt(0), lati.get(i));
 j.setValue((Attribute)training.elementAt(1), longi.get(i));
 j.setValue((Attribute)training.elementAt(2), post.get(i));
         
 isTrainingSet.add(j);     
            
        }
        Classifier cModel = (Classifier)new NaiveBayes();
 //Classifier cModel = (Classifier)new DecisionTable();
//Classifier cModel = (Classifier)new AttributeSelectedClassifier();
        
        
        cModel.buildClassifier(isTrainingSet);
 
 
 
 Instance test = new Instance(3);
 
 
 //test.setValue((Attribute)training.elementAt(0),13.1 );
 //test.setValue((Attribute)training.elementAt(1),121.9);
 latLongs = m.getLatLongPositions(g.loc2.getText());
 System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
 System.out.println(g.loc2.getText());
 
 
 
 test.setValue((Attribute)training.elementAt(0),Double.parseDouble(latLongs[0]) );
 test.setValue((Attribute)training.elementAt(1),Double.parseDouble(latLongs[1]));
 
 
 
 test.setDataset(isTrainingSet);
 
 double[] fDistribution = cModel.distributionForInstance(test);
double max=fDistribution[0];
int index=0;
 for(int i=1;i<fDistribution.length;i++)
{
    if(fDistribution[i]>max)
    {
        max=fDistribution[i];
        index=i;
    }
}

       // max = Arrays.stream(fDistribution).max().getAsDouble();  
 //System.out.println(fDistribution[0]+" "+fDistribution[1]);
        System.out.println(index+" "+max+" "+fDistribution[0]);
        g.answer.setText("the ambulance should go from post # "+index);
        
        Evaluation eTest = new Evaluation(isTrainingSet);
 //eTest.evaluateModel(cModel, isTestingSet);
      eTest.evaluateModel(cModel, isTrainingSet);
      String strSummary = eTest.toSummaryString();
 System.out.println(strSummary);
        
        
    
    }
   
   
   //method to list all the accidents info
   
   
   public static void listInfos( ){
      Session session = HibernateUtil.getSessionFactory().openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List infos = session.createQuery("FROM db.entity.Info").list(); 
         for (Iterator iterator1 = 
                           infos.iterator(); iterator1.hasNext();){
            Info tmp_info = (Info) iterator1.next(); 
            lati.add(tmp_info.getAccident().getAccLat());
            longi.add(tmp_info.getAccident().getAccLong());
            
            //String p=Integer.toString(tmp_info.getPost().getPost_id());
            post.add(tmp_info.getPost().getPost_name());
            //System.out.print("Post Name: " + tmp_info.getPost().getPost_name()); 
            //System.out.print("  Post Lat: " + tmp_info.getAccident().getAccLat()); 
            //System.out.println("  Post Lang: " + tmp_info.getAccident().getAccLong());
            //Set infos = tmp_info.getInfos();
            /*for (Iterator iterator2 = 
                         infos.iterator(); iterator2.hasNext();){
                  Info info = (Info) iterator2.next(); 
                  System.out.println("Info containing that post: " + info.getDistance());            }*/
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   
   
   
   
   public static void add()
   {
      System.out.println("Hibernate one to many (Annotation)");
        
        

        
	Session session = HibernateUtil.getSessionFactory().openSession();

        
        

	session.beginTransaction();

	Post post = new Post();
        post.setPost_name("10");
        post.setPost_lat(10.4);
         post.setPost_long(101.4);
        session.save(post);
        
        Accident acc=new Accident();
        acc.setAccLat(14.5);
        acc.setAccLoc("westr");
        acc.setAccLong(75.5);
        
        
        Info info = new Info();
        info.setTime(1.2);
        info.setDistance(12.1);
        
        
        info.setPost(post);  
        acc.setInfo(info);
        info.setAccident(acc);
        post.getInfos().add(info);
        
        session.save(acc);
        session.save(info);

	session.getTransaction().commit();
	System.out.println("Done");
       
       
   }
   
   
   public static void addPostAcc() throws Exception
   {
       //System.out.println("hahahah1");
       Session session = HibernateUtil.getSessionFactory().openSession();
	session.beginTransaction();

	Post post = new Post();
        post.setPost_name(g.po.getText());
        session.save(post);
        
        
         Accident acc=new Accident();
         //System.out.println("hahahah2");
          latLongs = m.getLatLongPositions(g.loc.getText());
         acc.setAccLat(Double.parseDouble(latLongs[0]));
        acc.setAccLoc(g.loc.getText());
        acc.setAccLong(Double.parseDouble(latLongs[1]));
         //System.out.println("hahahah3");
         System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
               Info info = new Info();
        info.setTime(1.2);
        info.setDistance(12.1);
        
        
        info.setPost(post);  
        acc.setInfo(info);
        info.setAccident(acc);
        post.getInfos().add(info);
        
        session.save(acc);
        session.save(info);
        
        session.getTransaction().commit();
      g.done.setText("data added");
       
   }

   
   
   
    
    
    
    
	public static void main(String[] args) {
		
            
            g.createGUI();
        
 try{
         factory = new Configuration().configure().buildSessionFactory();
      }catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }

 
  g.p_enter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             
             g.frame_predict.add(answer);
             g.answer.show();
            
             listInfos( );
             try {
                 Predict();
//listPosts();
             } catch (Exception ex) {
                 Logger.getLogger(executioner.class.getName()).log(Level.SEVERE, null, ex);
             }
         }          
      });
  
  
  
  g.add_enter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             g.frame_add.add(g.done);
             g.done.show();
             try {
                 /*g.frame.setVisible(false);
             
                 g.frame_add.setVisible(true);*/
                 System.out.println("hahahah1");
                 addPostAcc();
             } catch (Exception ex) {
                 Logger.getLogger(executioner.class.getName()).log(Level.SEVERE, null, ex);
             }
         }          
      });

  
 
         //listPosts();
         //listAccidents();
        
	}
}