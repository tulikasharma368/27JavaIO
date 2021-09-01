package io.employeePayroll;

import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class WatcherServices {
	
	private final WatchService watcher ;
	private final Map<WatchKey, Path> dirWatchers;
	
	//Create a watch service and register the given directory
	WatcherServices(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<WatchKey, Path>();
		scanAndRegisterDirectories(dir);
	}

	//Register the given directory with the watch services
	private void registerDirWatchers(Path dir) throws IOException{
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		dirWatchers.put(key, dir);
	}
	
	//Register the given directory and all it's sub directories with the watch services
	private void scanAndRegisterDirectories(final Path start) throws IOException {
		//register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException{
				registerDirWatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	 //Process all events for keys queued to the watcher
    @SuppressWarnings({"rawtypes", "unchecked"})
    void processEvents() {
        while (true) {
             WatchKey key; // wait for key to be signalled
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
            Path dir = dirWatchers.get(key);
            if (dir == null) {
                System.err.println("WatchKey not Recognized!!");
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
 
                // Context for directory entry event is the file name of entry
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
 
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
 
                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) {
                            scanAndRegisterDirectories(child);
                        }
                    } catch (IOException x) { }
                } else if (kind.equals(ENTRY_DELETE)) {
                	if (Files.isDirectory(child)) dirWatchers.remove(key);
                }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                dirWatchers.remove(key);
 
                // all directories are inaccessible
                if (dirWatchers.isEmpty()) {
                    break;
                }
            }
        }
    }
 
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("C:\\Users\\tyagi\\TempPlayGround");
        new WatcherServices(dir).processEvents();
    }
	
	

}
