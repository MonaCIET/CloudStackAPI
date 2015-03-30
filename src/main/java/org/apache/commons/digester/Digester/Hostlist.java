///*
// * 
// */
//package org.apache.commons.digester.Digester;
//
//import org.codehaus.plexus.digest.Digester;
//
///**
// *
// * @author mona
// */
//public class Hostlist {
//    
//    private String clusterid;
//            
//            String details;
//            
//        try {
//         //digester framework is introduced to request parameters
//          Digester digester=new digester();
//         
//           //validation is false.
//          digester.setValidating(false);
//        
//           //clusterid list the existing hosts in the particular cluster.
//         digester.addObjectCreate( "clusterid", clusterid.class );
//
//          // separated lists of the details requested.      
//          digester.addObjectCreate( "clusterid/details",details.class );
//           
//          //name of the host.
//          digester.addBeanPropertySetter("clusdterid/details/id", id.class);
//                  
//          // keyword mentioned by the cluster.
//          digester.addObjectCreate("clusterid/details/keyword", keyword.class);
//         
//          // identifier for the cluster.
//          digester.addObjectCreate("clusterid/details/name", name.class);
//                  
//          //added page property in the cluster.
//          digester.addBeanPropertySetter("clusterid/page", page.class);
//                  
//          //added pagesize in the cluster.
//          digester.addBeanPropertySetter("clusterid/pagesize", pagesize.class);
//          
//          //it gives lot of information about pod.
//          digester.addObjectCreate("clusterid/podid", podid.class);
//          
//          //
//          
//          
//                
//                
//      
//    
//}
