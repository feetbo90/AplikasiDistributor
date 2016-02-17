package distributor.app.material;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;

import retrofit.client.OkClient;

/**
 * Created by feetbo on 1/2/2016.
 */
public class ServiceGenerator {



    public static final String api_url = "http://192.168.43.35";

    static String sIPSetting = "";
    public void setURL(String url)
    {
        sIPSetting = url;
    }

    /*public String getURL()
    {
        return sIPSetting;
    }*/


   private static RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("http://"+ sIPSetting.trim())
           .setClient(new OkClient(new OkHttpClient()));


    public static<S> S createservice(Class<S> serviceclass) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceclass);
    }
   /* private static OkHttpClient okhttpklien;
    private static OkHttpClient sHttpClient = new OkHttpClient();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(api_url).addConverter(String.class, new StringConverter());


    public static<S> S createservice(Class<S> serviceclass){
        Retrofit retrofit= builder.client(okhttpklien).build();
        return retrofit.create(serviceclass);
    }
*/

/*    static {

        if (okhttpklien == null) {

            okhttpklien = new OkHttpClient();
            okhttpklien.setConnectTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setWriteTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setReadTimeout(60, TimeUnit.SECONDS);

        }

    }


    public static class StringConverter implements Converter<String> {

        public static String fromStream(InputStream in) throws IOException {

            BufferedSource sourceStr = Okio.buffer(Okio.source(in));

            String line1 = "";
            StringBuilder out1 = new StringBuilder();
            String newLine1 = System.getProperty("line.separator");

            while ((line1 = sourceStr.readUtf8Line()) != null) {
                out1.append(line1);
                out1.append(newLine1);
            }

            sourceStr.close();

            return out1.toString();
        }

        @Override
        public String fromBody(ResponseBody body) throws IOException {

            String text;
            try {

                text = fromStream(body.byteStream());

            } catch (Exception ex) {
                ex.printStackTrace();
                text = "";
            }


            return text;
        }

        @Override
        public RequestBody toBody(String value) {
            return null;
        }
    }
*/

}
