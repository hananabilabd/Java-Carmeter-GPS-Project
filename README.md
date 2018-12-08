# Java Carmeter GPS Project
   Connecting NMEA data from android phone to a PC via USB
   
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


here is the description of each term of the speed ,, assume that the speed is this:
$GPGGA,181908.00,3404.7041778,N,07044.3966270,W,4,13,1.00,495.144,M,29.200,M,0.10,0000*40
1. GP --> represent GPS and it might be GL which denotes GLONASS (Global Navigation Satellite System)
2. 181908.00 --> Time stamp for UTC (Coordinated Universal Time) 18 hour 19 minute and 08 second , for Egypt +2 which means 20h 19m 08s
3. 3404.7041778,N --> is the latitude in DMM.MMMMM format in degrees, minutes and decimal minutes (34 degree 04.7041778 N)
4. 07044.3966270,W --> is the longitude (70 deg 44.3966270 W)
5. 4 --> position of quality and vary from 0 to 8:
0= invalid   1=GPS fix( SPS)   2=DGPS fix   3=PPs fix   4= Real Time Kinematic (Centimeter precicion)   5=float Real Time Kinematic (decimeter precicion)  6=estimated (dead reckoning 2.3 feature)    7=Manual input mode   8=Simulation mode
6. 13 --> denotes number of satellites used in the coordinate
7. 1.00 --> denotes the HDOP (horizontal dilution of position)
8. 495.144,M --> altitud, meters, above sea level 
9. 29.200,M --> Height of geoid separation (geoid means ocean level) subtract this from the altitude of the antenna to arrive at the height above Ellipsoid
10. 0.10 --> time in seconds since last DGPS update
11. 0000 --> DGPS station ID number
12. *40 --> checksum data 
