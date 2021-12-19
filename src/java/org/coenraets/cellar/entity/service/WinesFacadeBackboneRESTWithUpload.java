/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coenraets.cellar.entity.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.List;
import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.coenraets.cellar.entity.Wine;
import javax.ws.rs.core.Response;
/**
 *
 * @author mauro
 */
@Stateless
@Path("wines")
public class WinesFacadeBackboneRESTWithUpload extends AbstractFacade<Wine> {

    @PersistenceContext(unitName = "winecellarPU")
    private EntityManager em;

    public WinesFacadeBackboneRESTWithUpload() {
        super(Wine.class);
    }

    @GET
    @Path("search/{query}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Wine> findByName(@PathParam("query") String query) {
        EntityManager em = this.getEntityManager();

        String ricerca = query;

        String querylike = "SELECT w FROM Wine w WHERE w.name LIKE CONCAT('%',:name ,'%')";
        System.out.println("\n\n" + "****-----------RESERCH WITH QUERY LIKE CONCAT---------------*****");
        System.out.println("querylike: " + querylike);
        System.out.println("param :name is= " + ricerca);

        List<Wine> list = em.createQuery(querylike, Wine.class).setParameter("name", ricerca).getResultList();

        System.out.println("founded:" + list.size() + " Wine ");
        System.out.println("\n\n" + "****-----------END RESERCH WITH QUERY LIKE CONCAT ----------------*****");
        return list;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Wine create(Wine entity) {
        System.out.println("creating wine");
        return super.create(entity);

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Wine edit(@PathParam("id") Integer id, Wine entity) {
        System.out.println("Updating wine: " + entity.getName());
        return super.edit(entity);
    }
 /* original method
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
*/
    // added OPTIONS and return a response 
    // @OPTIONS
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
  
     Response response;
        response = Response.status(204)   
                // header("yourHeaderName", "yourHeaderValue").build();
                .header("Allow","POST, PUT, GET, DELETE")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Content-Length", "0")
                .build();
        return response;
      }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Wine find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Wine> findAll() {
        return super.findAll();
    }
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Wine> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
  
    // method with path setted with servletContext
    @POST
    @Path("uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(List<org.apache.cxf.jaxrs.ext.multipart.Attachment> attachments, @Context HttpServletRequest request, @Context ServletContext servletContext) {
        for (org.apache.cxf.jaxrs.ext.multipart.Attachment attachment : attachments) {
            DataHandler handler = attachment.getDataHandler();
            try {
                InputStream stream = handler.getInputStream();
                MultivaluedMap<String, String> map = attachment.getHeaders();
                System.out.println("fileName Here" + getFileName(map));
               
                        
        String pathUploadDir =       servletContext.getRealPath("/pics/");
          
        System.out.println("the  path of upload dir is = "+pathUploadDir);
                     
          
       String namefile= getFileName(map);

String pathFinalOfFile=pathUploadDir+namefile;

 System.out.println("pathFinalOfFile is= "+pathFinalOfFile);
                
                                                 
   OutputStream out = new FileOutputStream(
           
           new File(pathFinalOfFile));                                                  
             
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                stream.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Response.ok("file uploaded").build();
    }
      private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "unknown";
    }
}
