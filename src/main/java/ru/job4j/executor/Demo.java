package ru.job4j.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable runnableTask = () -> {
            try {
                System.out.println("runnable is executing");
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Callable<String> callableTask = () -> {
            System.out.println("callableTask is executing");
            TimeUnit.MILLISECONDS.sleep(300);
            return "return Task's execution";
        };
        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        executor.execute(runnableTask);
        Future<String> future = executor.submit(callableTask);
        System.out.println("check here");
        try {
            List<Future<String>> result = executor.invokeAll(callableTasks);
            System.out.println("check here");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Future<String> future1 = executor.submit(callableTask);
        String result = null;
        try {
            result = future1.get();
            System.out.println("check here");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Future<String> resultFuture = scheduledExecutorService.schedule(callableTask, 1, TimeUnit.SECONDS);
        try {
            System.out.println("this is sheculed" + resultFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        scheduledExecutorService.shutdown();
        executor.shutdown();
    }
}
