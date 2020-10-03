package ru.itis.rasimusv.mvnDownloader.app;

import com.beust.jcommander.*;
import ru.itis.rasimusv.mvnDownloader.utils.*;
import java.util.concurrent.*;

public class Program {

    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder().addObject(argv).build().parse(args);

        Downloader downloader = new Downloader(argv.folder);

        if (argv.mode.equals("one-thread")) {
            for (String url : argv.files.split(",")) {
                downloader.download(url);
            }
        } else {
            ExecutorService es = Executors.newFixedThreadPool(argv.count);
            for (String file : argv.files.split(",")) {
                es.submit(() -> downloader.download(file));
            }
        }
    }
}