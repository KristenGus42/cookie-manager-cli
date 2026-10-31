//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.nio.file.Files;
import java.nio.file.Path;
// Inspo: https://www.baeldung.com/java-csv-file-array\

/**
 * Starts program
 */
void main()  {
    commandLoop(); // Start command loop
}

/**
 * Runs a command loop taking in user input
 */
void commandLoop() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Welcome to the cookie management system! Here you can manage cookie logs.\n");
    printOptions();

    String output = sc.nextLine();
    while(!(output.equals("q"))) {
        String[] outputArr = output.split(" ");

        String cmd = outputArr[0];
        int size = outputArr.length;

        if(cmd.equals("./h") && size == 1) {  // Option 1: Help
            printOptions();
        } else if(cmd.equals("./most_active_cookie") && size == 4) {  // Option 2: find most active cookie in system
            printMostActiveCookie(outputArr);
        }  else if(cmd.equals("./list") && size == 1) { // Option 3: List all files in system
            printExistingFileOptions();
        } else if(cmd.equals("./q") && size == 1) { // Option 4: Quit
            System.out.println("Thank you for using the cookie management system! Exiting program.");
            break;
        } else { // Invalid Command
            System.out.println("Unrecognized command. Use ./h to see command options.");
        }

        output = sc.nextLine();
    }
}


/**
 * Prints most active session cookies.
 * @param input string array representing command
 */
void printMostActiveCookie(String[] input)  {
    String fileName = input[1];
    String dateParam = input[2];
    String inputDate = input[3];
    List<List<String>> parsedLines = validateActiveCookieCmd(fileName,dateParam, inputDate);
    if(parsedLines.isEmpty()) return;

    HashMap<String, Integer> counts = new HashMap<>();

    int maxFreq = 0;
    HashSet<String> finalCookies = new HashSet<>();

    // Read file line by line
    for(List<String> line : parsedLines) {
        String cookie = line.get(0);
        String timeStamp = line.get(1);

        // Make sure not a header
        if(cookie.equals("cookie") || timeStamp.equals("timestamp")) continue;

        // Check if this cookie is on this date
        String curShortenedDate = timeStamp.substring(0, 10);
        if(curShortenedDate.equals(inputDate)) {
            counts.put(cookie, counts.getOrDefault(cookie, 0) + 1);

            if(maxFreq < counts.get(cookie)) {
                maxFreq = counts.get(cookie);
                finalCookies = new HashSet<>();
                finalCookies.add(cookie);
            } else if(maxFreq == counts.get(cookie)) {
                finalCookies.add(cookie);
            }
        }
    }

    if(counts.isEmpty()) {
        System.out.println("No cookies under " + inputDate + " found.\n");
    } else {
        for(String cookie: finalCookies) {
            System.out.println(cookie);
        }
        System.out.print("\n");
    }
}

/**
 * Prints existing csv file types within the data folder
 */
void printExistingFileOptions() {
    System.out.println("Available files within cookie management system:");
    System.out.println("cookie_log.csv\n"); // TODO: Implement dynamic printing later
}

/**
 * Prints all options for system
 */
private void printOptions() {
    System.out.println("./q                                                         Quit program");
    System.out.println("./h                                                         Displays command options");
    System.out.println("./list                                                      List available logs");
    System.out.println("./most_active_cookie [file name] -d [date: YYYY-MM-DD]      Retrieves the most active cookie from a specified date");
    System.out.println("\n");
}

/**
 * Checks if this active cookie command is valid and returns an object containing the contents of
 * a valid csv file.
 * The input array must be in format: [cmd] [csv file] [-d] [date]
 * @return List<List<String>> A list of lists representing each word in the csv file
 */
private List<List<String>> validateActiveCookieCmd(String fileName, String dateParam, String date) {
    // 1: Check if csv file
    String fileType = fileName.substring(fileName.length()-3);
    if(!(fileType.equals("csv"))) {
        System.out.println("Invalid file type please try again. Required type: .csv\n");
        return Collections.emptyList();
    }
    Path filePath = Paths.get("src", "data", fileName);

    // 2: Check if command is -d
    if(!(dateParam.equals("-d"))) {
        System.out.println("Invalid command, please include a date parameter flag. Most recent cookie command must include a date with the flag \"d\"\n");
        return Collections.emptyList();
    }

    // 3: Check if date is in right format
    String[] dateArr = date.split("-");
    if(dateArr[0].length() != 4 || Integer.parseInt(dateArr[0]) < 0 || Integer.parseInt(dateArr[0]) > 9999) {
        System.out.println("Invalid date. Please enter a valid year.\n");
        return Collections.emptyList();
    } else if(dateArr[1].length() != 2 || Integer.parseInt(dateArr[1]) < 1 || Integer.parseInt(dateArr[1]) > 12) {
        System.out.println("Invalid date. Please enter a valid month.\n");
        return Collections.emptyList();
    } else if(dateArr[2].length() != 2 ||Integer.parseInt(dateArr[2]) < 1 || Integer.parseInt(dateArr[2]) > 32) {
        System.out.println("Invalid date. Please enter a valid day.\n");
        return Collections.emptyList();
    }

    // Attempt to read file and catch any exceptions gracefully
    return readCsv(filePath);
}

/**
 * Given a csv file returns a list of lists representing each line
 * @param filePath Path object representing file path of csv data
 * @return List of lists representing each line. Returns and empty list if any exceptions are caught
 */
private static List<List<String>> readCsv(Path filePath) {
    try {
        return Files.readAllLines(filePath)
                .stream()
                .map(line -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
    } catch (NoSuchFileException e) {
        System.out.println("The file '" + filePath + "' does not exist. List available files using ./list");
    } catch (IOException e) {
        System.out.println("Couldn't read '" + filePath + "': " + e.getMessage());
    }
    return Collections.emptyList();
}


