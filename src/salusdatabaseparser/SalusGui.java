/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salusdatabaseparser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Konstantia
 */
public class SalusGui {
//DBConnect connect = new DBConnect();

    public String thePath;
    public String textFieldValue;
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/salus_forensics";
    //DB Credentials
    static final String USER = "root";
    static final String PASS = "12345";

    Connection conn = null;
    Statement stmt = null;

    public SalusGui() {
        gui();
    }

    public void gui() {

        JFrame mainWindow = new JFrame("Salus DB Tool");
        JButton button = new JButton("Select File");
        JButton buttonSqlite = new JButton("Select File");
        JButton buttonCaseNr = new JButton("Store Case ");
        JTextField caseNrHere = new JTextField("Write the Case Number Here ");

        caseNrHere.setBounds(10, 10, 220, 30);
        mainWindow.setSize(400, 300);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);

        JLabel labelJson = new JLabel("Click here to insert .json files");
        JLabel labelSqlite = new JLabel("Click here to insert .db files   ");

        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(Color.LIGHT_GRAY);

        buttonCaseNr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                textFieldValue = caseNrHere.getText();
                System.out.println(textFieldValue + "!");
            }
        });

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println(selectedFile.getPath());
                    thePath = selectedFile.getPath();
                    System.out.println(thePath);
                    if (thePath.contains("SMS")) {
                        String jsonData = readFile(thePath);
                        String insertSMS = createSMSInsertQuery(jsonData);
                        System.out.println(insertSMS);
                        
                    } else if (thePath.contains("IM")) {
                        String jsonData = readFile(thePath);
                        String[] split = thePath.split(" ", 2);
                        if (split[1].contains("Accounts")) {
                            String insertIMAccounts = createInstantAccountsInsertQuery(jsonData);
                            System.out.println(insertIMAccounts);
                        } else if (split[1].contains("Chats")) {
                            String insertIMChats = createIMChatsInsertQuery(jsonData);
                            System.out.println(insertIMChats);
                        } else if (split[1].contains("Contacts")) {
                            String insertIMContacts = createIMContactsInsertQuery(jsonData);
                            System.out.println(insertIMContacts);
                        } else if (split[1].contains("Messages")) {
                            String insertIMMessages = createIMMessagesInsertQuery(jsonData);
                            System.out.println(insertIMMessages);
                        }
                    } else if (thePath.contains("Accounts")) {
                        String jsonData = readFile(thePath);
                        String insertAccounts = createAccountsInsertQuery(jsonData);
                        System.out.println(insertAccounts);
                    } else if (thePath.contains("Audio External")) {
                        String jsonData = readFile(thePath);
                        String insertAudioExternal = createAudioExternalInsertQuery(jsonData);
                        System.out.println(insertAudioExternal);
                    } else if (thePath.contains("Bluetooth")) {
                        String jsonData = readFile(thePath);
                        String insertBluetooth = createBluetoothInsertQuery(jsonData);
                        System.out.println(insertBluetooth);
                    } else if (thePath.contains("Browser Bookmarks")) {
                        String jsonData = readFile(thePath);
                        String insertBrowserBookmarks = createBrowserBookmarksInsertQuery(jsonData);
                        System.out.println(insertBrowserBookmarks);
                    } else if (thePath.contains("Browser History")) {
                        String jsonData = readFile(thePath);
                        String insertBrowserHistory = createBrowserHistoryInsertQuery(jsonData);
                        System.out.println(insertBrowserHistory);
                    } else if (thePath.contains("Browser Searches")) {
                        String jsonData = readFile(thePath);
                        String insertBrowserSearches = createBrowserSearchesInsertQuery(jsonData);
                        System.out.println(insertBrowserSearches);
                    } else if (thePath.contains("Call Log")) {
                        String jsonData = readFile(thePath);
                        String insertCallLog = createCallLogInsertQuery(jsonData);
                        System.out.println(insertCallLog);
                    } else if (thePath.contains("Contacts ContactMethods")) {
                        String jsonData = readFile(thePath);
                        String insertContactsContactMethods = createContactsContactMethodsInsertQuery(jsonData);
                        System.out.println(insertContactsContactMethods);
                    } else if (thePath.contains("Contacts Groups")) {
                        String jsonData = readFile(thePath);
                        String insertContactsGroups = createContactsGroupsInsertQuery(jsonData);
                        System.out.println(insertContactsGroups);
                    } else if (thePath.contains("Contacts Organizations")) {
                        String jsonData = readFile(thePath);
                        String insertContactsOrganizations = createContactsOrganizationsInsertQuery(jsonData);
                        System.out.println(insertContactsOrganizations);
                    } else if (thePath.contains("Contacts Phones")) {
                        String jsonData = readFile(thePath);
                        String insertContactsPhones = createContactsPhonesInsertQuery(jsonData);
                        System.out.println(insertContactsPhones);
                    } else if (thePath.contains("Device Information")) {
                        String jsonData = readFile(thePath);
                        String insertDeviceInformation = createDeviceInformationInsertQuery(jsonData);
                        System.out.println(insertDeviceInformation);
                    } else if (thePath.contains("Images External")) {
                        String jsonData = readFile(thePath);
                        String insertImagesExternal = createImagesExternalInsertQuery(jsonData);
                        System.out.println(insertImagesExternal);
                    } else if (thePath.contains("Installed Apps")) {
                        String jsonData = readFile(thePath);
                        String insertInstalledApps = createInstalledAppsInsertQuery(jsonData);
                        System.out.println(insertInstalledApps);
                    } else if (thePath.contains("Videos")) {
                        String jsonData = readFile(thePath);
                        String[] split = thePath.split(" ", 2);
                        if (split[1].contains("External")) {
                            String insertVideosEx = createVideosExternalInsertQuery(jsonData);
                            System.out.println(insertVideosEx);
                        } else if (split[1].contains("Internal")) {
                            String insertVideosIn = createVideosInternalInsertQuery(jsonData);
                            System.out.println(insertVideosIn);
                        }
                    } else if (thePath.contains("WiFi")) {
                        String jsonData = readFile(thePath);
                        String insertWiFi = createWiFiInsertQuery(jsonData);
                        System.out.println(insertWiFi);
                    } else if (thePath.contains("MMS")) {
                        String jsonData = readFile(thePath);
                        String insertMMS = createMMSInsertQuery(jsonData);

                        System.out.println(insertMMS);
                    }
                }
            }
        });

        buttonSqlite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println(selectedFile.getName());
                    String dbPath = selectedFile.getPath();
                    String connectionDBLink = "jdbc:sqlite:" + dbPath;

                    String nervousBreakdown = sqliteConversion(connectionDBLink);
                    
                    String AudioInternalInput = sqliteConversionAudioInternal(connectionDBLink);
                    String CalendarEventsInput = sqliteConversionCalendarEvents(connectionDBLink);
                    String CalendarsInput = sqliteConversionCalendars(connectionDBLink);
                    String DownloadsInput = sqliteConversionDownloads(connectionDBLink);
                    String FilesExternalInput = sqliteConversionFilesExternal(connectionDBLink);
                    String FilesInternalInput = sqliteConversionFilesInternal(connectionDBLink);
                    String GmailInput = sqliteConversionGmail(connectionDBLink);
                    String GoogleAccountsInput = sqliteConversionGoogleAccounts(connectionDBLink);
                    String PeopleInput = sqliteConversionPeople(connectionDBLink);

                }
            }
        });

        mainPanel.add(caseNrHere);
        mainPanel.add(buttonCaseNr);
        mainPanel.add(labelJson);
        mainPanel.add(button);
        mainPanel.add(labelSqlite);
        mainPanel.add(buttonSqlite);
        mainWindow.add(mainPanel);

    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    //SMS.json 
    public String createSMSInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        
        StringBuffer sqlQuery = null;
        
        for (int i = 0; i < columns.length(); i++) {
            sqlQuery = new StringBuffer();
            
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export date
            String date = segment.getString("date");
            //Export body 
            String body = segment.getString("body");
            //Export message type
            String type = segment.getString("type");
            if (Integer.parseInt(type) == 1) {
                type = "Inbox";
            } else {
                type = "Sent";
            }
            //Export phone number aka address
            String address = segment.getString("address");
            //Export person
            String person = segment.getString("person");
            //Export read or not
            String ynread = segment.getString("read");
            //Export _id
            String _id = segment.getString("_id");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`sms` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(address);
            sqlQuery.append("','");
            sqlQuery.append(person);
            sqlQuery.append("',");
            sqlQuery.append(date);
            sqlQuery.append(",");
            sqlQuery.append(ynread);
            sqlQuery.append(",'");
            sqlQuery.append(type);
            sqlQuery.append("','");
            sqlQuery.append(body);
            sqlQuery.append("');");
            sqlQuery.append("\n");            
            //System.out.println(sqlQuery.toString());
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            //System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            //System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            //System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            //System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try

        }

        String insertSMSQuery = sqlQuery.toString();
        
        
        
        return insertSMSQuery;
    }

    //Accounts.json
    public String createAccountsInsertQuery(String s) {
        String viaextract_id = "";
        int _id = 0;
        JSONObject jobj = new JSONObject(s);
        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = null;
       

        for (int i = 0; i < columns.length(); i++) {
             
            sqlQuery=new StringBuffer();
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;
            _id = i + 1;

            //Export name
            String name = segment.getString("name");

            //Export  type
            String type = segment.getString("type");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`accounts` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(name);
            sqlQuery.append("','");
            sqlQuery.append(type);
            sqlQuery.append("');");
            
            
            System.out.println(sqlQuery.toString());
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try

        }

        String insertAccountsQuery = sqlQuery.toString();
        return insertAccountsQuery;
    }

    //Audio External.json
    public String createAudioExternalInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = null;
        

        for (int i = 0; i < columns.length(); i++) {
            
            sqlQuery = new StringBuffer();
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export _id
            String _id = segment.getString("_id");
            //Export path aka _data
            String _data = segment.getString("_data");
            //Export person
            String title = segment.getString("title");
            //Export size
            String _size = segment.getString("_size");
            //Export duration 
            String duration = segment.getString("duration");
            //Export date_added
            String date_added = segment.getString("date_added");
            //Export date_modified
            String date_modified = segment.getString("date_modified");
            //Export mime_type
            String mime_type = segment.getString("mime_type");
            //Export file_hash
            String file_hash = segment.getString("file_hash");
            //Export is_ringtone
            String is_ringtone = segment.getString("is_ringtone");
            //Export ve_filepath
            String ve_file_path = segment.getString("ve_file_path");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`audio_external` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(_data);
            sqlQuery.append("','");
            sqlQuery.append(title);
            sqlQuery.append("',");
            sqlQuery.append(_size);
            sqlQuery.append(",");
            sqlQuery.append(duration);
            sqlQuery.append(",");
            sqlQuery.append(date_added);
            sqlQuery.append(",");
            sqlQuery.append(date_modified);
            sqlQuery.append(",'");
            sqlQuery.append(mime_type);
            sqlQuery.append("','");
            sqlQuery.append(file_hash);
            sqlQuery.append("','");
            sqlQuery.append(is_ringtone);
            sqlQuery.append("','");
            sqlQuery.append(ve_file_path);
            sqlQuery.append("');");
            
            
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try

        }

        String insertAudioExternalQuery = sqlQuery.toString();

        return insertAudioExternalQuery;
    }

    //Bluetooth.json
    public String createBluetoothInsertQuery(String s) {
        String viaextract_id = "";
        int _id = 0;

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;
            _id = i + 1;

            //Export state
            int statenr = segment.getInt("state");

            //Export bonded devices
            JSONObject bondedDevices = segment.getJSONObject("bonded-devices");
            String bonded_devices = bondedDevices.toString();
            //Export scan mode
            int scan_mode = segment.getInt("scan-mode");
            //Export bluetooth MAC 
            String bluetooth_mac = segment.getString("bluetooth-mac");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`bluetooth` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",");
            sqlQuery.append(statenr);
            sqlQuery.append(",'");
            sqlQuery.append(bonded_devices);
            sqlQuery.append("',");
            sqlQuery.append(scan_mode);
            sqlQuery.append(",'");
            sqlQuery.append(bluetooth_mac);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertBluetoothQuery = sqlQuery.toString();

        return insertBluetoothQuery;
    }

    //Browser Bookmarks.json
    public String createBrowserBookmarksInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String title = segment.getString("title");
            String url = segment.getString("url");
            String visits = segment.getString("visits");
            String date = segment.getString("date");
            String bookmark = segment.getString("bookmark");
            String favicon = segment.getString("favicon");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`browser_bookmarks` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(title);
            sqlQuery.append("','");
            sqlQuery.append(url);
            sqlQuery.append("',");
            sqlQuery.append(visits);
            sqlQuery.append(",");
            sqlQuery.append(date);
            sqlQuery.append(",");
            sqlQuery.append(bookmark);
            sqlQuery.append(",'");
            sqlQuery.append(favicon);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertBrowserBookmarksQuery = sqlQuery.toString();

        return insertBrowserBookmarksQuery;
    }

    //Browser History.json
    public String createBrowserHistoryInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String title = segment.getString("title");
            String url = segment.getString("url");
            String visits = segment.getString("visits");
            String date = segment.getString("date");
            String bookmark = segment.getString("bookmark");
            String favicon = segment.getString("favicon");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`browser_history` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(title);
            sqlQuery.append("','");
            sqlQuery.append(url);
            sqlQuery.append("',");
            sqlQuery.append(visits);
            sqlQuery.append(",'");
            sqlQuery.append(date);
            sqlQuery.append("',");
            sqlQuery.append(bookmark);
            sqlQuery.append(",'");
            sqlQuery.append(favicon);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertBrowserHistoryQuery = sqlQuery.toString();

        return insertBrowserHistoryQuery;
    }

    //Browser Searches.json
    public String createBrowserSearchesInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);
        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String search = segment.getString("search");
            String date = segment.getString("date");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`browser_searches` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(search);
            sqlQuery.append("',");
            sqlQuery.append(date);
            sqlQuery.append(");\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertBrowserSearchesQuery = sqlQuery.toString();
        return insertBrowserSearchesQuery;
    }

    //Call Log.json
    public String createCallLogInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export _id
            String _id = segment.getString("_id");
            //Export number
            String number = segment.getString("number");
            //Export name
            String name = segment.getString("name");
            //Export date
            String date = segment.getString("date");
            //Export duration 
            String duration = segment.getString("duration");
            //Export type
            String type = segment.getString("type");
            if (Integer.parseInt(type) == 1) {
                type = "Incoming";
            } else if (Integer.parseInt(type) == 2) {
                type = "Outgoing";
            } else if (Integer.parseInt(type) == 3) {
                type = "Missed";
            } else {
                type = "Other";
            }
            //Export geocoded_location
            String geocoded_location = segment.getString("geocoded_location");
            //Export countryiso
            String countryiso = segment.getString("countryiso");
            //Export voicemail_uri
            String voicemail_uri = segment.getString("voicemail_uri");
            //Export lookup_uri
            String lookup_uri = segment.getString("lookup_uri");
            //Export frequent
            String frequent = segment.getString("frequent");
            //Export contactid
            String contactid = segment.getString("contactid");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`call_log` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(number);
            sqlQuery.append("','");
            sqlQuery.append(name);
            sqlQuery.append("',");
            sqlQuery.append(date);
            sqlQuery.append(",");
            sqlQuery.append(duration);
            sqlQuery.append(",'");
            sqlQuery.append(type);
            sqlQuery.append("','");
            sqlQuery.append(geocoded_location);
            sqlQuery.append("','");
            sqlQuery.append(countryiso);
            sqlQuery.append("','");
            sqlQuery.append(voicemail_uri);
            sqlQuery.append("','");
            sqlQuery.append(lookup_uri);
            sqlQuery.append("',");
            sqlQuery.append(frequent);
            sqlQuery.append(",");
            sqlQuery.append(contactid);
            sqlQuery.append(");\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertCallLogQuery = sqlQuery.toString();

        return insertCallLogQuery;
    }

    //Contacts ContactMethods.json
    public String createContactsContactMethodsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);
        JSONArray columns = jobj.getJSONArray("data");
        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String display_name = segment.getString("display_name");
            String data = segment.getString("data");
            String last_time_contacted = segment.getString("last_time_contacted");
            String times_contacted = segment.getString("times_contacted");
            String person = segment.getString("person");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`contacts_contactmethods` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(display_name);
            sqlQuery.append("','");
            sqlQuery.append(data);
            sqlQuery.append("','");
            sqlQuery.append(last_time_contacted);
            sqlQuery.append("','");
            sqlQuery.append(times_contacted);
            sqlQuery.append("','");
            sqlQuery.append(person);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertContactsContactMethodsQuery = sqlQuery.toString();

        return insertContactsContactMethodsQuery;
    }

    //Contacts Groups.json
    public String createContactsGroupsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String notes = segment.getString("notes");
            String system_id = segment.getString("system_id");
            String name = segment.getString("name");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`contacts_groups` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(notes);
            sqlQuery.append("','");
            sqlQuery.append(system_id);
            sqlQuery.append("','");
            sqlQuery.append(name);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertContactsGroupsQuery = sqlQuery.toString();

        return insertContactsGroupsQuery;
    }

    //Contacts Organizations.json
    public String createContactsOrganizationsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String title = segment.getString("title");
            String company = segment.getString("company");
            String person = segment.getString("person");
            String type = segment.getString("type");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`contacts_organizations` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(title);
            sqlQuery.append("','");
            sqlQuery.append(company);
            sqlQuery.append("',");
            sqlQuery.append(person);
            sqlQuery.append(",");
            sqlQuery.append(type);
            sqlQuery.append(");\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertContactsOrganizationsQuery = sqlQuery.toString();

        return insertContactsOrganizationsQuery;
    }

    //Contacts Phones.json
    public String createContactsPhonesInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String display_name = segment.getString("display_name");
            String number = segment.getString("number");
            String times_contacted = segment.getString("times_contacted");
            String last_time_contacted = segment.getString("last_time_contacted");
            String starred = segment.getString("starred");
            String primary_phone = segment.getString("primary_phone");
            String primary_email = segment.getString("primary_email");
            String person = segment.getString("person");
            String send_to_voicemail = segment.getString("send_to_voicemail");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`contacts_phones` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(display_name);
            sqlQuery.append("','");
            sqlQuery.append(number);
            sqlQuery.append("',");
            sqlQuery.append(times_contacted);
            sqlQuery.append(",'");
            sqlQuery.append(last_time_contacted);
            sqlQuery.append("',");
            sqlQuery.append(starred);
            sqlQuery.append(",'");
            sqlQuery.append(primary_phone);
            sqlQuery.append("','");
            sqlQuery.append(primary_email);
            sqlQuery.append("',");
            sqlQuery.append(person);
            sqlQuery.append(",'");
            sqlQuery.append(send_to_voicemail);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertContactsPhonesQuery = sqlQuery.toString();

        return insertContactsPhonesQuery;
    }

    //Device Information.json
    public String createDeviceInformationInsertQuery(String s) {
        String viaextract_id = "";
        int _id = 0;

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;
            _id = i + 1;

            String model = segment.getString("model");
            String brand = segment.getString("brand");
            String device = segment.getString("device");
            String IMEI_MEID = segment.getString("IMEI-MEID");
            String IMSI = segment.getString("IMSI");
            String ICCID = segment.getString("ICCID");
            String MSISDN_MDN = segment.getString("MSISDN-MDN");
            String version_release = segment.getString("version.release");
            String version_sdk = segment.getString("version.sdk");
            String product = segment.getString("product");
            String fingerprint = segment.getString("fingerprint");
            String type = segment.getString("type");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`device_information` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(model);
            sqlQuery.append("','");
            sqlQuery.append(brand);
            sqlQuery.append("','");
            sqlQuery.append(device);
            sqlQuery.append("','");
            sqlQuery.append(IMEI_MEID);
            sqlQuery.append("','");
            sqlQuery.append(IMSI);
            sqlQuery.append("','");
            sqlQuery.append(ICCID);
            sqlQuery.append("','");
            sqlQuery.append(MSISDN_MDN);
            sqlQuery.append("','");
            sqlQuery.append(version_release);
            sqlQuery.append("','");
            sqlQuery.append(version_sdk);
            sqlQuery.append("','");
            sqlQuery.append(product);
            sqlQuery.append("','");
            sqlQuery.append(fingerprint);
            sqlQuery.append("','");
            sqlQuery.append(type);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertDeviceInformationQuery = sqlQuery.toString();

        return insertDeviceInformationQuery;
    }

    //IM Accounts.json
    public String createInstantAccountsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String name = segment.getString("name");
            String username = segment.getString("username");
            String locked = segment.getString("locked");
            String keep_signed_in = segment.getString("keep_signed_in");
            String last_login_state = segment.getString("last_login_state");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`im_accounts` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(name);
            sqlQuery.append("','");
            sqlQuery.append(username);
            sqlQuery.append("','");
            sqlQuery.append(locked);
            sqlQuery.append("','");
            sqlQuery.append(keep_signed_in);
            sqlQuery.append("','");
            sqlQuery.append(last_login_state);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertIMAccountsQuery = sqlQuery.toString();

        return insertIMAccountsQuery;
    }

    //Images External.json
    public String createImagesExternalInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export _id
            String _id = segment.getString("_id");
            //Export path aka _data
            String _data = segment.getString("_data");
            //Export person
            String title = segment.getString("title");
            //Export size
            String _size = segment.getString("_size");
            //Export date_taken
            String datetaken = segment.getString("datetaken");
            //Export date_added
            String date_added = segment.getString("date_added");
            //Export date_modified
            String date_modified = segment.getString("date_modified");
            //Export mime_type
            String mime_type = segment.getString("mime_type");
            //Export is_ringtone
            String latitude = segment.getString("latitude");
            //Export ve_filepath
            String longitude = segment.getString("longitude");
            //Export file_hash
            String file_hash = segment.getString("file_hash");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`images_external` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(_data);
            sqlQuery.append("','");
            sqlQuery.append(title);
            sqlQuery.append("',");
            sqlQuery.append(_size);
            sqlQuery.append(",");
            sqlQuery.append(datetaken);
            sqlQuery.append(",");
            sqlQuery.append(date_added);
            sqlQuery.append(",");
            sqlQuery.append(date_modified);
            sqlQuery.append(",'");
            sqlQuery.append(mime_type);
            sqlQuery.append("','");
            sqlQuery.append(latitude);
            sqlQuery.append("','");
            sqlQuery.append(longitude);
            sqlQuery.append("','");
            sqlQuery.append(file_hash);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertImagesExternalQuery = sqlQuery.toString();

        return insertImagesExternalQuery;
    }

    //Installed Apps.json
    public String createInstalledAppsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export uid
            String uid = segment.getString("uid");
            //Export label
            String label = segment.getString("label");
            //Export packageName
            String packageName = segment.getString("packageName");
            //Export dataDir
            String dataDir = segment.getString("dataDir");
            //Export versionName
            String versionName = segment.getString("versionName");
            //Export enabled
            String enabled = segment.getString("enabled");
            //Export versionCode
            String versionCode = segment.getString("versionCode");
            //Export permission
            String permission = segment.getString("permission");
            //Export processName
            String processName = segment.getString("processName");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`installed_apps` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(uid);
            sqlQuery.append(",'");
            sqlQuery.append(label);
            sqlQuery.append("','");
            sqlQuery.append(packageName);
            sqlQuery.append("','");
            sqlQuery.append(dataDir);
            sqlQuery.append("','");
            sqlQuery.append(versionName);
            sqlQuery.append("',");
            sqlQuery.append(enabled);
            sqlQuery.append(",'");
            sqlQuery.append(versionCode);
            sqlQuery.append("','");
            sqlQuery.append(permission);
            sqlQuery.append("','");
            sqlQuery.append(processName);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertInstalledAppsQuery = sqlQuery.toString();

        return insertInstalledAppsQuery;
    }

    //Videos External.json
    public String createVideosExternalInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export _id
            String _id = segment.getString("_id");
            //Export path aka _data
            String _data = segment.getString("_data");
            //Export person
            String title = segment.getString("title");
            //Export size
            String _size = segment.getString("_size");
            //Export date_taken
            String datetaken = segment.getString("datetaken");
            //Export date_added
            String date_added = segment.getString("date_added");
            //Export date_modified
            String date_modified = segment.getString("date_modified");
            //Export mime_type
            String mime_type = segment.getString("mime_type");
            //Export is_ringtone
            String latitude = segment.getString("latitude");
            //Export longitude
            String longitude = segment.getString("longitude");
            //Export duration
            String duration = segment.getString("duration");
            //Export resolution
            String resolution = segment.getString("resolution");
            //Export file_hash
            String file_hash = segment.getString("file_hash");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`videos_external` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(_data);
            sqlQuery.append("','");
            sqlQuery.append(title);
            sqlQuery.append("',");
            sqlQuery.append(_size);
            sqlQuery.append(",");
            sqlQuery.append(datetaken);
            sqlQuery.append(",");
            sqlQuery.append(date_added);
            sqlQuery.append(",");
            sqlQuery.append(date_modified);
            sqlQuery.append(",'");
            sqlQuery.append(mime_type);
            sqlQuery.append("','");
            sqlQuery.append(latitude);
            sqlQuery.append("','");
            sqlQuery.append(longitude);
            sqlQuery.append("','");
            sqlQuery.append(duration);
            sqlQuery.append("','");
            sqlQuery.append(resolution);
            sqlQuery.append("','");
            sqlQuery.append(file_hash);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertVideosExternalQuery = sqlQuery.toString();

        return insertVideosExternalQuery;
    }

    //Videos Internal.json
    public String createVideosInternalInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            //Export _id
            String _id = segment.getString("_id");
            //Export  _data
            String _data = segment.getString("_data");
            //Export person
            String title = segment.getString("title");
            //Export size
            String _size = segment.getString("_size");
            //Export date_taken
            String datetaken = segment.getString("datetaken");
            //Export date_added
            String date_added = segment.getString("date_added");
            //Export date_modified
            String date_modified = segment.getString("date_modified");
            //Export mime_type
            String mime_type = segment.getString("mime_type");
            //Export is_ringtone
            String latitude = segment.getString("latitude");
            //Export longitude
            String longitude = segment.getString("longitude");
            //Export duration
            String duration = segment.getString("duration");
            //Export resolution
            String resolution = segment.getString("resolution");
            //Export file_hash
            String file_hash = segment.getString("file_hash");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`videos_internal` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(_data);
            sqlQuery.append("','");
            sqlQuery.append(title);
            sqlQuery.append("',");
            sqlQuery.append(_size);
            sqlQuery.append(",");
            sqlQuery.append(datetaken);
            sqlQuery.append(",");
            sqlQuery.append(date_added);
            sqlQuery.append(",");
            sqlQuery.append(date_modified);
            sqlQuery.append(",'");
            sqlQuery.append(mime_type);
            sqlQuery.append("','");
            sqlQuery.append(latitude);
            sqlQuery.append("','");
            sqlQuery.append(longitude);
            sqlQuery.append("','");
            sqlQuery.append(duration);
            sqlQuery.append("','");
            sqlQuery.append(resolution);
            sqlQuery.append("','");
            sqlQuery.append(file_hash);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertVideosInternalQuery = sqlQuery.toString();

        return insertVideosInternalQuery;
    }

    //WiFi.json
    public String createWiFiInsertQuery(String s) {
        String viaextract_id = "";
        int _id = 0;

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;
            _id = i + 1;

            String ssid = segment.getString("ssid");
            String bssid = segment.getString("bssid");
            String status = segment.getString("status");
            int priority = segment.getInt("priority");
            String hiddenSSID = segment.getString("hiddenSSID");
            int networkId = segment.getInt("networkId");
            JSONArray wepKeysJ = segment.getJSONArray("wepKeys");
            String wepKeys = wepKeysJ.toString();
            int wepTxKeyIndex = segment.getInt("wepTxKeyIndex");
            JSONObject allowedGroupCiphersJ = segment.getJSONObject("allowedGroupCiphers");
            String allowedGroupCiphers = allowedGroupCiphersJ.toString();
            JSONObject allowedPairwiseCiphersJ = segment.getJSONObject("allowedPairwiseCiphers");
            String allowedPairwiseCiphers = allowedPairwiseCiphersJ.toString();
            JSONObject allowedAuthAlgorithmsJ = segment.getJSONObject("allowedAuthAlgorithms");
            String allowedAuthAlgorithms = allowedAuthAlgorithmsJ.toString();

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`wifi` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",'");
            sqlQuery.append(ssid);
            sqlQuery.append("','");
            sqlQuery.append(bssid);
            sqlQuery.append("','");
            sqlQuery.append(status);
            sqlQuery.append("','");
            sqlQuery.append(priority);
            sqlQuery.append("','");
            sqlQuery.append(hiddenSSID);
            sqlQuery.append("','");
            sqlQuery.append(networkId);
            sqlQuery.append("','");
            sqlQuery.append(wepKeys);
            sqlQuery.append("','");
            sqlQuery.append(wepTxKeyIndex);
            sqlQuery.append("','");
            sqlQuery.append(allowedGroupCiphers);
            sqlQuery.append("','");
            sqlQuery.append(allowedPairwiseCiphers);
            sqlQuery.append("','");
            sqlQuery.append(allowedAuthAlgorithms);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertWiFiQuery = sqlQuery.toString();

        return insertWiFiQuery;
    }

    //MMS.json
    public String createMMSInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String thread_id = segment.getString("thread_id");
            String date = segment.getString("date");
            String date_sent = segment.getString("date_sent");
            String m_type = segment.getString("m_type");
            String m_size = segment.getString("m_size");
            String retr_text = segment.getString("retr_text");
            String resp_text = segment.getString("resp_text");
            String seen = segment.getString("seen");
            String app_id = segment.getString("app_id");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`mms` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append(",");
            sqlQuery.append(thread_id);
            sqlQuery.append(",");
            sqlQuery.append(date);
            sqlQuery.append(",");
            sqlQuery.append(date_sent);
            sqlQuery.append(",'");
            sqlQuery.append(m_type);
            sqlQuery.append("',");
            sqlQuery.append(m_size);
            sqlQuery.append(",'");
            sqlQuery.append(retr_text);
            sqlQuery.append("','");
            sqlQuery.append(resp_text);
            sqlQuery.append("',");
            sqlQuery.append(seen);
            sqlQuery.append(",'");
            sqlQuery.append(app_id);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertMMSQuery = sqlQuery.toString();

        return insertMMSQuery;
    }

    //IM Chats.json
    public String createIMChatsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String jid_resource = segment.getString("jid_resource");
            String groupchat = segment.getString("groupchat");
            String last_unread_message = segment.getString("last_unread_message");
            String last_message_date = segment.getString("last_message_date");
            String unsent_composed_message = segment.getString("unsent_composed_message");
            String account_id = segment.getString("account_id");
            String local = segment.getString("local");
            String otherClient = segment.getString("seen");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`im_chats` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append("','");
            sqlQuery.append(jid_resource);
            sqlQuery.append("','");
            sqlQuery.append(groupchat);
            sqlQuery.append("','");
            sqlQuery.append(last_unread_message);
            sqlQuery.append("',");
            sqlQuery.append(last_message_date);
            sqlQuery.append(",'");
            sqlQuery.append(unsent_composed_message);
            sqlQuery.append("','");
            sqlQuery.append(account_id);
            sqlQuery.append("','");
            sqlQuery.append(local);
            sqlQuery.append("','");
            sqlQuery.append(otherClient);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertIMChatsQuery = sqlQuery.toString();

        return insertIMChatsQuery;
    }

    //IM Contacts.json
    public String createIMContactsInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String avatars_data = segment.getString("avatars_data");
            String contact_id = segment.getString("contact_id");
            String last_unread_message = segment.getString("last_unread_message");
            String username = segment.getString("username");
            String contactList = segment.getString("contactList");
            String rejected = segment.getString("rejected");
            String client_type = segment.getString("client_type");
            String chats_contact_id = segment.getString("chats_contact_id");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`im_contacts` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append("','");
            sqlQuery.append(avatars_data);
            sqlQuery.append("','");
            sqlQuery.append(contact_id);
            sqlQuery.append("','");
            sqlQuery.append(last_unread_message);
            sqlQuery.append("','");
            sqlQuery.append(username);
            sqlQuery.append("','");
            sqlQuery.append(contactList);
            sqlQuery.append("','");
            sqlQuery.append(rejected);
            sqlQuery.append("','");
            sqlQuery.append(client_type);
            sqlQuery.append("',");
            sqlQuery.append(chats_contact_id);
            sqlQuery.append(");\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertIMContactsQuery = sqlQuery.toString();

        return insertIMContactsQuery;
    }

    //IM Messages.json  
    public String createIMMessagesInsertQuery(String s) {
        String viaextract_id = "";

        JSONObject jobj = new JSONObject(s);

        JSONArray columns = jobj.getJSONArray("data");

        StringBuffer sqlQuery = new StringBuffer();

        for (int i = 0; i < columns.length(); i++) {
            JSONObject segment = (JSONObject) columns.get(i); //Main data container for the JSON Object in the data array

            viaextract_id = textFieldValue;

            String _id = segment.getString("_id");
            String thread_id = segment.getString("thread_id");
            String err_code = segment.getString("err_code");
            String err_msg = segment.getString("err_msg");
            String send_status = segment.getString("send_status");
            String message_read = segment.getString("message_read");

            sqlQuery.append("INSERT IGNORE INTO `salus_forensics`.`im_messages` VALUES (");
            sqlQuery.append(viaextract_id);
            sqlQuery.append(",");
            sqlQuery.append(_id);
            sqlQuery.append("','");
            sqlQuery.append(thread_id);
            sqlQuery.append("','");
            sqlQuery.append(err_code);
            sqlQuery.append("','");
            sqlQuery.append(err_msg);
            sqlQuery.append("','");
            sqlQuery.append(send_status);
            sqlQuery.append("','");
            sqlQuery.append(message_read);
            sqlQuery.append("');\n");
            
            try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqlQuery.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
        }

        String insertIMMessagesQuery = sqlQuery.toString();

        return insertIMMessagesQuery;
    }

    public String sqliteConversion(String s) {
        try {

            String viaextract_idFB = "";
            // Connecting To sqliteDatabase
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);
            StringBuffer sqliteQueryFileBrowser = new StringBuffer();
            
            System.out.println("Opened database successfully");
            int _id = 0;
            // OPERATION
            Statement stat = c.createStatement();
            ResultSet rsFileBrowser = stat.executeQuery("SELECT * FROM _filebrowser;");

            //INSERT INTO __FILEBROWSER-----------------------------------------
            while (rsFileBrowser.next()) {

                viaextract_idFB = textFieldValue;

                _id++;
                String __fullpath = rsFileBrowser.getString(1);
                String __parent = rsFileBrowser.getString(2);
                String basename = rsFileBrowser.getString(3);
                String flatname = rsFileBrowser.getString(4);
                String size = rsFileBrowser.getString(5);
                String children_len = rsFileBrowser.getString(6);
                String binary = rsFileBrowser.getString(7);
                String preview = rsFileBrowser.getString(8);
                String file_type = rsFileBrowser.getString(9);
                String file_mime = rsFileBrowser.getString(10);
                String metadata = rsFileBrowser.getString(11);

                sqliteQueryFileBrowser.append("INSERT IGNORE INTO `salus_forensics`.`_filebrowser` VALUES (");
                sqliteQueryFileBrowser.append(viaextract_idFB);
                sqliteQueryFileBrowser.append(",");
                sqliteQueryFileBrowser.append(_id);
                sqliteQueryFileBrowser.append(",'");
                sqliteQueryFileBrowser.append(__fullpath);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(__parent);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(basename);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(flatname);
                sqliteQueryFileBrowser.append("',");
                sqliteQueryFileBrowser.append(size);
                sqliteQueryFileBrowser.append(",");
                sqliteQueryFileBrowser.append(children_len);
                sqliteQueryFileBrowser.append(",");
                sqliteQueryFileBrowser.append(binary);
                sqliteQueryFileBrowser.append(",'");
                sqliteQueryFileBrowser.append(preview);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(file_type);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(file_mime);
                sqliteQueryFileBrowser.append("','");
                sqliteQueryFileBrowser.append(metadata);
                sqliteQueryFileBrowser.append("');");
                System.out.println(sqliteQueryFileBrowser);
                 try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryFileBrowser.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                 
                 sqliteQueryFileBrowser.setLength(0);
            }


            //System.out.println(sqliteQueryFileBrowser);
            rsFileBrowser.close();

           
            c.close();
            
          
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        
        return s;
    }


    public String sqliteConversionAudioInternal(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryAudioInternal = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPPERATION
            Statement stat = c.createStatement();

            ResultSet rsAudioInternal = stat.executeQuery("SELECT * FROM audio_internal;");

            //INSERT INTO AUDIO INTERNAL-----------------------------------------------
            while (rsAudioInternal.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsAudioInternal.getString(2);
                String _data = rsAudioInternal.getString(3);
                String title = rsAudioInternal.getString(4);
                String _size = rsAudioInternal.getString(5);
                String duration = rsAudioInternal.getString(6);
                String date_added = rsAudioInternal.getString(7);
                String date_modified = rsAudioInternal.getString(8);
                String mime_type = rsAudioInternal.getString(9);
                String latitude = rsAudioInternal.getString(10);
                String longitude = rsAudioInternal.getString(11);
                String is_ringtone = rsAudioInternal.getString(12);

                sqliteQueryAudioInternal.append("INSERT IGNORE INTO `salus_forensics`.`audio_internal` VALUES (");
                sqliteQueryAudioInternal.append(__viaextract_id);
                sqliteQueryAudioInternal.append(",");
                sqliteQueryAudioInternal.append(_id);
                sqliteQueryAudioInternal.append(",'");
                sqliteQueryAudioInternal.append(_data);
                sqliteQueryAudioInternal.append("','");
                sqliteQueryAudioInternal.append(title);
                sqliteQueryAudioInternal.append("',");
                sqliteQueryAudioInternal.append(_size);
                sqliteQueryAudioInternal.append(",");
                sqliteQueryAudioInternal.append(duration);
                sqliteQueryAudioInternal.append(",");
                sqliteQueryAudioInternal.append(date_added);
                sqliteQueryAudioInternal.append(",");
                sqliteQueryAudioInternal.append(date_modified);
                sqliteQueryAudioInternal.append(",'");
                sqliteQueryAudioInternal.append(mime_type);
                sqliteQueryAudioInternal.append("','");
                sqliteQueryAudioInternal.append(latitude);
                sqliteQueryAudioInternal.append("','");
                sqliteQueryAudioInternal.append(longitude);
                sqliteQueryAudioInternal.append("',");
                sqliteQueryAudioInternal.append(is_ringtone);
                sqliteQueryAudioInternal.append(");");
                System.out.println(sqliteQueryAudioInternal);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryAudioInternal.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                
             sqliteQueryAudioInternal.setLength(0);
            }
            System.out.println(sqliteQueryAudioInternal);

            rsAudioInternal.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionCalendarEvents(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryCalendarEvents = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPPERATION
            Statement stat = c.createStatement();

            ResultSet rsCalendarEvents = stat.executeQuery("SELECT * FROM calendar_events;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsCalendarEvents.next()) {

                String __viaextract_id = textFieldValue;
                String id = rsCalendarEvents.getString(2);
                String title = rsCalendarEvents.getString(3);
                String location = rsCalendarEvents.getString(4);
                String description = rsCalendarEvents.getString(5);
                String dateStart = rsCalendarEvents.getString(6);
                String dateEnd = rsCalendarEvents.getString(7);
                String accountName = rsCalendarEvents.getString(8);
                String calendarName = rsCalendarEvents.getString(9);
                String calendarTimezone = rsCalendarEvents.getString(10);
                String dateStartRaw = rsCalendarEvents.getString(11);
                String dateEndRaw = rsCalendarEvents.getString(12);
                String lastDateRaw = rsCalendarEvents.getString(13);

                sqliteQueryCalendarEvents.append("INSERT IGNORE INTO `salus_forensics`.`calendar_events` VALUES (");
                sqliteQueryCalendarEvents.append(__viaextract_id);
                sqliteQueryCalendarEvents.append(",");
                sqliteQueryCalendarEvents.append(id);
                sqliteQueryCalendarEvents.append(",'");
                sqliteQueryCalendarEvents.append(title);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(location);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(description);
                sqliteQueryCalendarEvents.append("',");
                sqliteQueryCalendarEvents.append(dateStart);
                sqliteQueryCalendarEvents.append(",");
                sqliteQueryCalendarEvents.append(dateEnd);
                sqliteQueryCalendarEvents.append(",'");
                sqliteQueryCalendarEvents.append(accountName);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(calendarName);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(calendarTimezone);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(dateStartRaw);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(dateEndRaw);
                sqliteQueryCalendarEvents.append("','");
                sqliteQueryCalendarEvents.append(lastDateRaw);
                sqliteQueryCalendarEvents.append("');");
                 System.out.println(sqliteQueryCalendarEvents);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryCalendarEvents.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryCalendarEvents.setLength(0);
            }
            System.out.println(sqliteQueryCalendarEvents);

            rsCalendarEvents.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionCalendars(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryCalendars = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPPERATION
            Statement stat = c.createStatement();

            ResultSet rsCalendars = stat.executeQuery("SELECT * FROM calendars;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsCalendars.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsCalendars.getString(2);
                String accountName = rsCalendars.getString(3);
                String accountType = rsCalendars.getString(4);
                String name = rsCalendars.getString(5);
                String calendarDisplayName = rsCalendars.getString(6);
                String calendarTimezone = rsCalendars.getString(7);
                String deleted = rsCalendars.getString(8);
                String ownerAccount = rsCalendars.getString(9);
                String cal_sync8 = rsCalendars.getString(10);

                sqliteQueryCalendars.append("INSERT IGNORE INTO `salus_forensics`.`calendars` VALUES (");
                sqliteQueryCalendars.append(__viaextract_id);
                sqliteQueryCalendars.append(",");
                sqliteQueryCalendars.append(_id);
                sqliteQueryCalendars.append(",'");
                sqliteQueryCalendars.append(accountName);
                sqliteQueryCalendars.append("','");
                sqliteQueryCalendars.append(accountType);
                sqliteQueryCalendars.append("','");
                sqliteQueryCalendars.append(name);
                sqliteQueryCalendars.append("','");
                sqliteQueryCalendars.append(calendarDisplayName);
                sqliteQueryCalendars.append("','");
                sqliteQueryCalendars.append(calendarTimezone);
                sqliteQueryCalendars.append(",'");
                sqliteQueryCalendars.append(accountName);
                sqliteQueryCalendars.append("',");
                sqliteQueryCalendars.append(deleted);
                sqliteQueryCalendars.append(",'");
                sqliteQueryCalendars.append(calendarTimezone);
                sqliteQueryCalendars.append("','");
                sqliteQueryCalendars.append(ownerAccount);
                sqliteQueryCalendars.append("',");
                sqliteQueryCalendars.append(cal_sync8);
                sqliteQueryCalendars.append(");");
                System.out.println(sqliteQueryCalendars);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryCalendars.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryCalendars.setLength(0);
            }
            
            rsCalendars.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionDownloads(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryDownloads = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPPERATION
            Statement stat = c.createStatement();

            ResultSet rsDownloads = stat.executeQuery("SELECT * FROM downloads;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsDownloads.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsDownloads.getString(2);
                String uri = rsDownloads.getString(3);
                String _data = rsDownloads.getString(4);
                String lastmod = rsDownloads.getString(5);
                String total_bytes = rsDownloads.getString(6);
                String title = rsDownloads.getString(7);
                String description = rsDownloads.getString(8);

                sqliteQueryDownloads.append("INSERT IGNORE INTO `salus_forensics`.`downloads` VALUES (");
                sqliteQueryDownloads.append(__viaextract_id);
                sqliteQueryDownloads.append(",");
                sqliteQueryDownloads.append(_id);
                sqliteQueryDownloads.append(",'");
                sqliteQueryDownloads.append(uri);
                sqliteQueryDownloads.append("','");
                sqliteQueryDownloads.append(_data);
                sqliteQueryDownloads.append("',");
                sqliteQueryDownloads.append(lastmod);
                sqliteQueryDownloads.append(",");
                sqliteQueryDownloads.append(total_bytes);
                sqliteQueryDownloads.append(",'");
                sqliteQueryDownloads.append(title);
                sqliteQueryDownloads.append("','");
                sqliteQueryDownloads.append(description);
                sqliteQueryDownloads.append("');");
                System.out.println(sqliteQueryDownloads);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryDownloads.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryDownloads.setLength(0);
            }
            

            rsDownloads.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionFilesExternal(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryFilesExternal = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPPERATION
            Statement stat = c.createStatement();

            ResultSet rsFilesExternal = stat.executeQuery("SELECT * FROM files_external;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsFilesExternal.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsFilesExternal.getString(2);
                String _data = rsFilesExternal.getString(3);
                String title = rsFilesExternal.getString(4);
                String _size = rsFilesExternal.getString(5);
                String date_added = rsFilesExternal.getString(6);
                String date_modified = rsFilesExternal.getString(7);
                String mime_type = rsFilesExternal.getString(8);
                String latitude = rsFilesExternal.getString(9);
                String longitude = rsFilesExternal.getString(10);

                sqliteQueryFilesExternal.append("INSERT IGNORE INTO `salus_forensics`.`files_external` VALUES (");
                sqliteQueryFilesExternal.append(__viaextract_id);
                sqliteQueryFilesExternal.append(",");
                sqliteQueryFilesExternal.append(_id);
                sqliteQueryFilesExternal.append(",'");
                sqliteQueryFilesExternal.append(_data);
                sqliteQueryFilesExternal.append("','");
                sqliteQueryFilesExternal.append(title);
                sqliteQueryFilesExternal.append("',");
                sqliteQueryFilesExternal.append(_size);
                sqliteQueryFilesExternal.append(",");
                sqliteQueryFilesExternal.append(date_added);
                sqliteQueryFilesExternal.append(",");
                sqliteQueryFilesExternal.append(date_modified);
                sqliteQueryFilesExternal.append(",'");
                sqliteQueryFilesExternal.append(mime_type);
                sqliteQueryFilesExternal.append("','");
                sqliteQueryFilesExternal.append(latitude);
                sqliteQueryFilesExternal.append("','");
                sqliteQueryFilesExternal.append(longitude);
                sqliteQueryFilesExternal.append("');");
                System.out.println(sqliteQueryFilesExternal);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryFilesExternal.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryFilesExternal.setLength(0);
            }
            

            rsFilesExternal.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionFilesInternal(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryFilesInternal = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPERATION
            Statement stat = c.createStatement();

            ResultSet rsFilesInternal = stat.executeQuery("SELECT * FROM files_internal;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsFilesInternal.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsFilesInternal.getString(2);
                String _data = rsFilesInternal.getString(3);
                String title = rsFilesInternal.getString(4);
                String _size = rsFilesInternal.getString(5);
                String date_added = rsFilesInternal.getString(6);
                String date_modified = rsFilesInternal.getString(7);
                String mime_type = rsFilesInternal.getString(8);
                String latitude = rsFilesInternal.getString(9);
                String longitude = rsFilesInternal.getString(10);

                sqliteQueryFilesInternal.append("INSERT IGNORE INTO `salus_forensics`.`files_internal` VALUES (");
                sqliteQueryFilesInternal.append(__viaextract_id);
                sqliteQueryFilesInternal.append(",");
                sqliteQueryFilesInternal.append(_id);
                sqliteQueryFilesInternal.append(",'");
                sqliteQueryFilesInternal.append(_data);
                sqliteQueryFilesInternal.append("','");
                sqliteQueryFilesInternal.append(title);
                sqliteQueryFilesInternal.append("',");
                sqliteQueryFilesInternal.append(_size);
                sqliteQueryFilesInternal.append(",");
                sqliteQueryFilesInternal.append(date_added);
                sqliteQueryFilesInternal.append(",");
                sqliteQueryFilesInternal.append(date_modified);
                sqliteQueryFilesInternal.append(",'");
                sqliteQueryFilesInternal.append(mime_type);
                sqliteQueryFilesInternal.append("','");
                sqliteQueryFilesInternal.append(latitude);
                sqliteQueryFilesInternal.append("','");
                sqliteQueryFilesInternal.append(longitude);
                sqliteQueryFilesInternal.append("');");
                System.out.println(sqliteQueryFilesInternal);
                
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryFilesInternal.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryFilesInternal.setLength(0);
            }
            

            rsFilesInternal.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionGmail(String s) {
        try {

            int _id = 0;
            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryGmail = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPERATION
            Statement stat = c.createStatement();

            ResultSet rsGmail = stat.executeQuery("SELECT * FROM gmail;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsGmail.next()) {

                _id++;
                String __viaextract_id = textFieldValue;
                String date_sent = rsGmail.getString(2);
                String date_received = rsGmail.getString(3);
                String subject = rsGmail.getString(4);
                String sender = rsGmail.getString(5);
                String recipients = rsGmail.getString(6);
                String cc = rsGmail.getString(7);
                String bcc = rsGmail.getString(8);
                String snippet = rsGmail.getString(9);
                String body = rsGmail.getString(10);
                String filenames = rsGmail.getString(11);

                sqliteQueryGmail.append("INSERT IGNORE INTO `salus_forensics`.`gmail` VALUES (");
                sqliteQueryGmail.append(__viaextract_id);
                sqliteQueryGmail.append(",");
                sqliteQueryGmail.append(_id);
                sqliteQueryGmail.append(",");
                sqliteQueryGmail.append(date_sent);
                sqliteQueryGmail.append(",");
                sqliteQueryGmail.append(date_received);
                sqliteQueryGmail.append(",'");
                sqliteQueryGmail.append(subject);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(sender);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(recipients);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(cc);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(bcc);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(snippet);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(body);
                sqliteQueryGmail.append("','");
                sqliteQueryGmail.append(filenames);
                sqliteQueryGmail.append("');\n");
                System.out.println(sqliteQueryGmail);
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryGmail.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryGmail.setLength(0);
            }
            

            rsGmail.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionGoogleAccounts(String s) {
        try {

            int _id = 0;
            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryGoogleAccounts = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPERATION
            Statement stat = c.createStatement();

            ResultSet rsGoogleAccounts = stat.executeQuery("SELECT * FROM google_account;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsGoogleAccounts.next()) {

                _id++;
                String __viaextract_id = textFieldValue;
                String account_name = rsGoogleAccounts.getString(2);
                String display_name = rsGoogleAccounts.getString(3);
                String cover_photo_url = rsGoogleAccounts.getString(4);
                String last_sync_start_time = rsGoogleAccounts.getString(5);
                String last_sync_finish_time = rsGoogleAccounts.getString(6);
                String last_successful_sync_time = rsGoogleAccounts.getString(7);

                sqliteQueryGoogleAccounts.append("INSERT IGNORE INTO `salus_forensics`.`google_account` VALUES (");
                sqliteQueryGoogleAccounts.append(__viaextract_id);
                sqliteQueryGoogleAccounts.append(",");
                sqliteQueryGoogleAccounts.append(_id);
                sqliteQueryGoogleAccounts.append(",'");
                sqliteQueryGoogleAccounts.append(account_name);
                sqliteQueryGoogleAccounts.append("','");
                sqliteQueryGoogleAccounts.append(display_name);
                sqliteQueryGoogleAccounts.append("','");
                sqliteQueryGoogleAccounts.append(cover_photo_url);
                sqliteQueryGoogleAccounts.append("','");
                sqliteQueryGoogleAccounts.append(last_sync_start_time);
                sqliteQueryGoogleAccounts.append("','");
                sqliteQueryGoogleAccounts.append(last_sync_finish_time);
                sqliteQueryGoogleAccounts.append("','");
                sqliteQueryGoogleAccounts.append(last_successful_sync_time);
                sqliteQueryGoogleAccounts.append("');");
                System.out.println(sqliteQueryGoogleAccounts);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryGoogleAccounts.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryGoogleAccounts.setLength(0);
            }
            

            rsGoogleAccounts.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionPeople(String s) {
        try {

            int _id = 0;
            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryPeople = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPERATION
            Statement stat = c.createStatement();

            ResultSet rsPeople = stat.executeQuery("SELECT * FROM people;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsPeople.next()) {

                _id++;
                String __viaextract_id = textFieldValue;
                String display_name = rsPeople.getString(2);
                String deleted = rsPeople.getString(3);
                String starred = rsPeople.getString(4);
                String home_number = rsPeople.getString(5);
                String mobile_number = rsPeople.getString(6);
                String work_number = rsPeople.getString(7);
                String home_email_address = rsPeople.getString(8);
                String work_email_address = rsPeople.getString(9);
                String other_email_address = rsPeople.getString(10);
                String im_name = rsPeople.getString(11);
                String picture = rsPeople.getString(12);
                String company = rsPeople.getString(13);
                String job_title = rsPeople.getString(14);
                String times_contacted = rsPeople.getString(15);
                String last_time_contacted = rsPeople.getString(16);
                String anniversary = rsPeople.getString(17);
                String birthday = rsPeople.getString(18);
                String home_address = rsPeople.getString(19);
                String work_address = rsPeople.getString(20);
                String website = rsPeople.getString(21);
                String notes = rsPeople.getString(22);

                sqliteQueryPeople.append("INSERT IGNORE INTO `salus_forensics`.`people` VALUES (");
                sqliteQueryPeople.append(__viaextract_id);
                sqliteQueryPeople.append(",");
                sqliteQueryPeople.append(_id);
                sqliteQueryPeople.append(",'");
                sqliteQueryPeople.append(display_name);
                sqliteQueryPeople.append("',");
                sqliteQueryPeople.append(deleted);
                sqliteQueryPeople.append(",");
                sqliteQueryPeople.append(starred);
                sqliteQueryPeople.append(",'");
                sqliteQueryPeople.append(home_number);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(mobile_number);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(work_number);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(home_email_address);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(work_email_address);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(other_email_address);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(im_name);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(picture);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(company);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(job_title);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(times_contacted);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(last_time_contacted);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(anniversary);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(birthday);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(home_address);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(work_address);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(website);
                sqliteQueryPeople.append("','");
                sqliteQueryPeople.append(notes);
                sqliteQueryPeople.append("');");
                System.out.println(sqliteQueryPeople);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryPeople.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryPeople.setLength(0);
            }
            

            rsPeople.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

    public String sqliteConversionVideosInternal(String s) {
        try {

            // Connecting To Database
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(s);

            StringBuffer sqliteQueryVideosInternal = new StringBuffer();
            System.out.println("Opened database successfully");

            // OPERATION
            Statement stat = c.createStatement();

            ResultSet rsVideosInternal = stat.executeQuery("SELECT * FROM videos_internal;");

            //INSERT INTO ANALYST-----------------------------------------------
            while (rsVideosInternal.next()) {

                String __viaextract_id = textFieldValue;
                String _id = rsVideosInternal.getString(2);
                String _data = rsVideosInternal.getString(3);
                String title = rsVideosInternal.getString(4);
                String _size = rsVideosInternal.getString(5);
                String date_taken = rsVideosInternal.getString(6);
                String date_added = rsVideosInternal.getString(7);
                String date_modified = rsVideosInternal.getString(8);
                String mime_type = rsVideosInternal.getString(9);
                String duration = rsVideosInternal.getString(10);
                String latitude = rsVideosInternal.getString(11);
                String longitude = rsVideosInternal.getString(12);

                sqliteQueryVideosInternal.append("INSERT IGNORE INTO `videos_internal` VALUES (");
                sqliteQueryVideosInternal.append(__viaextract_id);
                sqliteQueryVideosInternal.append(",");
                sqliteQueryVideosInternal.append(_id);
                sqliteQueryVideosInternal.append(",'");
                sqliteQueryVideosInternal.append(_data);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(title);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(_size);
                sqliteQueryVideosInternal.append("',");
                sqliteQueryVideosInternal.append(date_taken);
                sqliteQueryVideosInternal.append(",");
                sqliteQueryVideosInternal.append(date_added);
                sqliteQueryVideosInternal.append(",");
                sqliteQueryVideosInternal.append(date_modified);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(mime_type);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(duration);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(latitude);
                sqliteQueryVideosInternal.append("','");
                sqliteQueryVideosInternal.append(longitude);
                sqliteQueryVideosInternal.append("');");
                System.out.println(sqliteQueryVideosInternal);
                
                try {
                            //STEP 2: Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //STEP 3: Open a connection
                            System.out.println("Connecting to a selected database...");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Sti vasi mpikes, tha pareis t arxidia sou meta...");
                            
                            System.out.println("terma ta difragka...");
                            stmt = conn.createStatement();
                            
                            stmt.executeUpdate(sqliteQueryVideosInternal.toString());
                            System.out.println("ta kataferes porni...");
                            
                        } catch (SQLException se) {
                            //Handle errors for JDBC
                            se.printStackTrace();
                        } catch (Exception e) {
                            //Handle errors for Class.forName
                            e.printStackTrace();
                        } finally {
                            //finally block used to close resources
                            try {
                                if (stmt != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                            }// do nothing
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                sqliteQueryVideosInternal.setLength(0);
            }
            

            rsVideosInternal.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return s;
    }

}
