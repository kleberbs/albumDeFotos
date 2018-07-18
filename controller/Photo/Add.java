package controller.Photo;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PMF;


import javax.servlet.RequestDispatcher;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import model.Foto;



@SuppressWarnings("serial")
public class Add extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
				 RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/Photo/add.jsp");
				 try {
				
					 rd.forward(req, resp);
					 } 
				 catch (ServletException e) {			
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 }
	}
			 
		

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		 List<BlobKey> blobKeys = blobs.get("photo");
		 String photo="/serve?blob-key=" + blobKeys.get(0).getKeyString();
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		final Foto p = new Foto(name, description, photo);
		try{
			pm.makePersistent(p);
			resp.getWriter().println("Datos grabados correctamente.");
			resp.sendRedirect("/listPhoto");
		}catch(Exception e){
			System.out.println(e);
			resp.getWriter().print(e);
		}finally{
			pm.close();
		}
		
	}
}
