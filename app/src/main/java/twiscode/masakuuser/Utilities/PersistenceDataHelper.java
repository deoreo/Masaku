package twiscode.masakuuser.Utilities;


/**
 * Created by User on 13/04/2015.
 */
public class PersistenceDataHelper {
    // kelas utama sebagai penyimpan data temporay dari setiap proses yang ada di dalam apps
    private static PersistenceDataHelper _persistanceIntanceHelper ;// instance dari kelas
    public DataFragmentHelper FragmentHelper;// Kelas bantuan untuk perpindahan fragment

    private PersistenceDataHelper(){

        FragmentHelper = new DataFragmentHelper();
    }

    public static PersistenceDataHelper GetInstance(){
        if( _persistanceIntanceHelper == null)
        {
            _persistanceIntanceHelper = new PersistenceDataHelper();
        }
        return _persistanceIntanceHelper;
    }



}
