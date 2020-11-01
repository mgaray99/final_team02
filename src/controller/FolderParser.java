package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FolderParser {

  private List<String> matchingFilenames;
  private Set<String> acceptableFileExtensions;
  private File folder;

  /**
   * @param path - the filepath leading to the folder to be parsed
   * @param firstExtension - the first acceptable extension
   */
  public FolderParser(String path, String firstExtension) {
    matchingFilenames = new ArrayList<>();
    acceptableFileExtensions = new HashSet<>();
    acceptableFileExtensions.add(firstExtension);
    folder = new File(path);
    fillListWithFilenames();
  }

  /**
   * Fills the list matchingFilenames with all files that match the extensions contained in
   * acceptableFileExtensions in folder
   */
  private void fillListWithFilenames() {
    for (File configFile : folder.listFiles()) {
      String fileName = configFile.getName();
      if (fileName.indexOf('.') != -1 && acceptableFileExtensions
          .contains(fileName.substring(fileName.indexOf('.'))))
        matchingFilenames.add(fileName.substring(0, fileName.indexOf('.')));
      }
    }

  /**
   * Adds the string extension (i.e. ".jpeg") to the list of acceptable file extensions that a file
   * may have in order to be added to matching filenames and then updates matchingFilenames
   * @param extension the extension to be added
   */
  public void addAcceptableExtension(String extension) {
        acceptableFileExtensions.add(extension);
        recalculateMatchingFilenames();
  }

  /**
   * Recalculates the contents of the matchingFilenames list by first clearing it of all Strings
   * and then proceeding to refill it given the contents of acceptableFileExtensions
   */
  private void recalculateMatchingFilenames() {
    matchingFilenames.clear();
    fillListWithFilenames();
  }

  /**
   * Defensively copies the matchingFilenames list (all String names of files in a folder with an
   * acceptable extension) and then returns it
   * @return filenamesFound, which contains the contents of matchingFilenames
   */
  public List<String> getFilenamesFromFolder() {
    List<String> filenamesFound = new ArrayList<>();
    filenamesFound.addAll(matchingFilenames);
    return filenamesFound;
  }
}
