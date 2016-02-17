package distributor.app.material;



import retrofit.Callback;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

import retrofit.mime.TypedFile;


/*
 * Created by feetbo on 1/2/2016.
 */
public interface Apis {

    /**
     * TES AMBIL DATA
     **/
    public static final String api_url = "http://192.168.43.35";

    @Multipart
    @POST("/media/flupi.php")
    void upload(@Part("uploaded_file")TypedFile file,
         Callback<String>cb);




}
