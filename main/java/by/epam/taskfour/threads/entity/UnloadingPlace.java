package by.epam.taskfour.threads.entity;

import by.epam.taskfour.threads.exception.UnloadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UnloadingPlace {
    public static Logger logger = LogManager.getLogger();
    private static final int NUMBER_OF_RAMPS = 7;
    private static UnloadingPlace unloading;
    private static ReentrantLock lock = new ReentrantLock();

    private Semaphore semaphore = new Semaphore(NUMBER_OF_RAMPS);
    private List<Ramp> ramps = new ArrayList<Ramp>();

    private UnloadingPlace() {
        for (int i = 0; i < NUMBER_OF_RAMPS; i++) {
            Ramp ramp = new Ramp(i);
            ramps.add(ramp);
        }
    }

    public static UnloadingPlace getUnloadingPlace() {
        if (unloading == null) {
            try {
                lock.lock();
                if (unloading == null) {
                    unloading = new UnloadingPlace();
                    logger.info("Unloading place was created");
                }
            } finally {
                lock.unlock();
            }
        }
        return unloading;
    }

    public Ramp getRamp(Truck truck) throws UnloadingException {
        try {
            logger.info("Arrived at the unloading place " + truck.toString());
            semaphore.acquire();
            lock.lock();
            for (Ramp ramp : ramps) {
                if (!ramp.isBusy()) {
                    ramp.setBusy(true);
                    logger.info("There is a " + truck.toString() + " on the ramp number " + ramp.getId());
                    return ramp;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UnloadingException("Current thread " + Thread.currentThread().getName() + " was interrupted");
        } finally {
            lock.unlock();
        }
        throw new UnloadingException("the number of ramps does not match the number of semaphore permits");
    }

    public void releaseRamp(Truck truck, Ramp ramp) {
        ramp.setBusy(false);
        semaphore.release();
        logger.info("Truck " + truck.getNumberTruck() + " left the ramp number " + ramp.getId());
    }
}
