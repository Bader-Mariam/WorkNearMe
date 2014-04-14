package com.worknearme;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dan, Bader
 *
 * This class is used to update the jobs cache in a separate thread as to not
 * block incoming requests.
 *
 */
public class updateJobsCacheTimerTask extends TimerTask {

    JobsCache cache = JobsCache.getInstance();

    @Override
    public void run() {
        try {
            cache.updateJobs();
        } catch (IOException ex) {
            Logger.getLogger(updateJobsCacheTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
