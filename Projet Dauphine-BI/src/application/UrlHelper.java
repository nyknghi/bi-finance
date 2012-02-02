package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Administrator
 * Télécharge un fichier selon une adresse à une destination donnée (optionnelle)
 */
public class UrlHelper {

	public static void downloadFile(String adresse) {

		downloadFile(adresse, null);
	}

	public static void downloadFile(String adresse, File dest) {
		BufferedReader reader = null;
		FileOutputStream fos = null;
		InputStream in = null;
		try {

			// création de la connection
			URL url = new URL(adresse);
			URLConnection conn = url.openConnection();
			System.out.println(adresse);

			String FileType = conn.getContentType();
			System.out.println("FileType : " + FileType);

			// lecture de la réponse
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			if (dest == null) {
				String FileName = "Stock.csv";;
				dest = new File(FileName);
			}
			fos = new FileOutputStream(dest);
			byte[] buff = new byte[1024];
			int l = in.read(buff);
			while (l > 0) {
				fos.write(buff, 0, l);
				l = in.read(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
