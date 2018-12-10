# Java Carmeter GPS Project
   Connecting NMEA data from android phone to a PC via USB
## Video
   Recorded Video about the project Idea and how to use and run it --> [link](https://youtu.be/5dTuUQ5fDOY)
 <p align="center">
  <img  src="https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/Images/FlowChart.PNG">
</p>

## **How to use**
### First
1.  Download this Repository to your local machine

### Installation of [Share GPS app](https://play.google.com/store/apps/details?id=com.jillybunch.shareGPS&hl=en)
1.	Download and install share GPS app from google play
2.	In Share GPS, create a new connection for NMEA USB. The default port of 50000 should be fine for most.
3.  Allow developer mode in android then alow USB debugging.

### Installation of [com0com](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/tree/master/Dependancies/com0com-2.2.2.0-x64-fre-signed)
1.  Install com0com 
2.  Open setup of com0com and change the first textbox to COM15 like in the photo below
![alt text](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/Images/c0c-setup.png)
3.  Click apply then close this windows 

### Installation of [StandAlone ADB](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/Dependancies/minimal_adb_fastboot_v1.4.3_setup.exe)
1.  Install this [.exe](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/Dependancies/minimal_adb_fastboot_v1.4.3_setup.exe)

### Installation of [com2tcp](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/tree/master/Dependancies/com2tcp-1.3.0.0-386)
1.  Copy all items in this folder to where ADB is installed (propably C:\Program Files (x86)\Minimal ADB and Fastboot)
2.  Copy [gps.bat](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/gps.bat) to (ADB Folder) then create a shortcut of this gps.bat to desktop to be accessed easily.

### Finally 
1.  Install [JDK 8 x86 version](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) as there are problem inversion x64 of  rxtx library 
2.  Open netbeans.conf in C:\Program Files\NetBeans 8.2\etc
3.  Change the following line `netbeans_jdkhome="put you jdk x86 here"`
4.  Open netbeans and run Carmeter.java

## Dependancies and packages used 
Their jars are all included (no need to download anything other than this repository).
1.  [GMapsFX](https://github.com/rterp/GMapsFX)
2.  [Medusa](https://github.com/HanSolo/Medusa)
3.  [Marine API](https://github.com/ktuukkan/marine-api)
4.  [RXTXCOMM](http://rxtx.qbang.org/wiki/index.php/Main_Page)

you may refer to this [website](http://www.catb.org/gpsd/NMEA.html#_rmc_recommended_minimum_navigation_information) to see Nmea sentence structure


## Here is the description of GPS Sentence
      **Assume those sentences are the output:**
             **First One is:**
**$GPGGA,181908.00,3404.7041778,N,07044.3966270,W,4,13,1.00,495.144,M,29.200,M,0.10,0000*40** 
1. *GPGGA*     --> represent GPS and it might be GL which denotes GLONASS (Global Navigation Satellite System)
2. *181908.00* --> Time stamp for UTC (Coordinated Universal Time) 18 hour 19 minute and 08 second , for Egypt +2 which means 20h 19m 08s
3. *3404.7041778,N*  --> is the latitude in DMM.MMMMM format in degrees, minutes and decimal minutes (34 degree 04.7041778 N)
4. *07044.3966270,W* --> is the longitude (70 deg 44.3966270 W)
5. *4* --> position of quality and vary from 0 to 8:
0= invalid   1=GPS fix( SPS)   2=DGPS fix   3=PPs fix   4= Real Time Kinematic (Centimeter precicion)   5=float Real Time Kinematic (decimeter precicion)  6=estimated (dead reckoning 2.3 feature)    7=Manual input mode   8=Simulation mode
6. *13*   --> denotes number of satellites used in the coordinate
7. *1.00* --> denotes the HDOP (horizontal dilution of position)
8. *495.144,M* --> altitud, meters, above sea level 
9. *29.200,M*  --> Height of geoid separation (geoid means ocean leve) subtract this from the altitude of the antenna to arrive at the height above Ellipsoid
10. *0.10* --> time in seconds since last DGPS update
11. *0000* --> DGPS station ID number
12. _40_   --> checksum data 

***NOTE:*** DGPS (Differential GPS):  is essentially a system to provide positional corrections to GPS signals. DGPS uses a fixed, known position to adjust real time GPS signals to eliminate pseudorange errors, DGPS corrections improve the accuracy of position data only. it has no effect on results that are based on speed data, such as brake stop results.
for more information about DGPS you can visit [website](https://racelogic.support/01VBOX_Automotive/01General_Information/Knowledge_Base/How_does_DGPS_(Differential_GPS)_work%3F)

             **Second One is:**
**$GPGLL,4916.45,N,12311.12,W,225444,A,*1D**
1. *GLL*        --> Geographic position, Latitude and Longitude
2. *4916.45,N*  --> Latitude 49 deg. 16.45 min. North
3. *12311.12,W* --> Longitude 123 deg. 11.12 min. West
4. *225444*     --> Fix taken at 22:54:44 UTC
5. *A*          --> Data Active or V (void)
6. _*1D_        --> checksum data

             **Third One is:
**$GPRMC,123519,A,4807.038,N,01131.000,E,022.4,084.4,230394,003.1,W*6A**
1.  *RMC*         --> Recommended Minimum sentence C
2.  *123519*      --> Fix taken at 12:35:19 UTC (convert according to time zone, for Egypt UTC+2)
3.  *A*           --> Navigation receiver warning A = OK (means that you are getting a signal and things are working), V = warning (means you do not have a signal)
4.  *4807.038,N*  --> Latitude 48 deg 07.038' minutes Northern Hemishpere
5.  *01131.000,E* --> Longitude 11 deg 31.000' minutes in Eastern Hemisphere
6.  *022.4*       --> Speed over the ground in knots
7.  *084.4*       --> Track angle in degrees True
8.  *230394*      --> Date - 23rd of March 1994 (Day , Month , Year)
9.  *003.1,W*     --> Magnetic Variation 3.1 deg West
10. _*6A_         --> The checksum data & always begins with *

## Graphical User Interface (GUI)
![alt text](https://github.com/hananabilabd/Java-Carmeter-GPS-Project/blob/master/Images/gui4.PNG)
1. Add Medusa library to libraries (Medusa.jar)
2. Make CSS Package include css.css file (css file include the colors codes for Buttons and linear-gradient for background).
3. Make 3 Buttons start(to start reading speed and position), stop and sound off (turn off the alarm speed over 20K).
4. Set Action for each Button
5. Set color and area for each Button
6. Make gauge to display the speed
7. Make GridPane to add the objects(like Buttons ,gauge,..)
8. Make 3 Labels (longitude – latitude – Speed)
9. Make 3 Text to show the values for longitude,latitude and speed
10. Set the position for Buttons, gauge,Labels,Text 
11. Make HBox and add Map and your gridPane on it 
12. Make StackPane to add background to your GUI
13. put your stackPane on scene 
14. Then put the scene on Stage
15. Rename the Stage
16. Make a GPS icon for stage

