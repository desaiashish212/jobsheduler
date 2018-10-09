package plugin.bmb.com.jobsheduler;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;


import java.util.LinkedList;
/**
 * Created by AD on 10/7/2018.
 */

public class DemoJobService  extends JobService {
    private static final String TAG = "DemoJobService";
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: " + params.getJobId());
        //Right hear background service
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: " + params.getJobId());
        return true;
    }
}
