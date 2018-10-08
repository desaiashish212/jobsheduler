package plugin.bmb.com.jobsheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_UNCOLOUR_START = 0;
    public static final int MSG_UNCOLOUR_STOP = 1;
    public static final int MSG_SERVICE_OBJ = 2;
    int defaultColor;
    int startJobColor;
    int stopJobColor;
    private EditText etDelay;
    private EditText etDeadline;
    private RadioButton rbWiFiConnectivity;
    private RadioButton rbAnyConnectivity;
    private CheckBox cbCharging;
    private CheckBox cbIdle;
    private Button btnSchedule;
    private Button btnCancel;
    ComponentName mServiceComponent;
    private static int jobId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etDelay = (EditText) findViewById(R.id.delay_time);
        etDeadline = (EditText) findViewById(R.id.deadline_time);
        rbWiFiConnectivity = (RadioButton) findViewById(R.id.checkbox_unmetered);
        rbAnyConnectivity = (RadioButton) findViewById(R.id.checkbox_any);
        cbCharging = (CheckBox) findViewById(R.id.checkbox_charging);
        cbIdle = (CheckBox) findViewById(R.id.checkbox_idle);
        btnSchedule = (Button) findViewById(R.id.btnSchedule);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAllJobs();
            }
        });
        mServiceComponent = new ComponentName(this, DemoJobService.class);
        Resources res = getResources();
        defaultColor = res.getColor(R.color.none_received);
        startJobColor = res.getColor(R.color.start_received);
        stopJobColor = res.getColor(R.color.stop_received);


    }

    public void scheduleJob() {

        // Create Job with all the constraints
        JobInfo.Builder builder = new JobInfo.Builder(jobId++,mServiceComponent);
        String delay = etDelay.getText().toString();
        if (delay != null && !TextUtils.isEmpty(delay)) {
            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
        }
        String deadline = etDeadline.getText().toString();
        if (deadline != null && !TextUtils.isEmpty(deadline)) {
            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
        }
        boolean requiresUnmetered = rbWiFiConnectivity.isChecked();
        boolean requiresAnyConnectivity = rbAnyConnectivity
                .isChecked();
        if (requiresUnmetered) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }
        builder.setRequiresDeviceIdle(cbIdle.isChecked());
        builder.setRequiresCharging(cbCharging.isChecked());
        JobScheduler jobScheduler =
                (JobScheduler) getApplication().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // Schedule the job
        jobScheduler.schedule(builder.build());
    }
    public void cancelAllJobs() {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
    }
}
