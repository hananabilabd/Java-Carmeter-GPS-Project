# Java Carmeter GPS Project
her is the description of each term of the speed ,, assume that the speed is this:
$GPGGA,181908.00,3404.7041778,N,07044.3966270,W,4,13,1.00,495.144,M,29.200,M,0.10,0000*40
GP --> represent GPS and it might be GL which denotes GLONASS (Global Navigation Satellite System)
181908.00 --> Time stamp for UTC (Coordinated Universal Time) 18 hour 19 minute and 08 second , for Egypt +2 which means 20h 19m 08s
3404.7041778,N --> is the latitude in DMM.MMMMM format in degrees, minutes and decimal minutes (34 degree 04.7041778 N)
07044.3966270,W --> is the longitude (70 deg 44.3966270 W)
4 --> position of quality and vary from 0 to 8:
0= invalid   1=GPS fix( SPS)   2=DGPS fix   3=PPs fix   4= Real Time Kinematic (Centimeter precicion)   5=float Real Time Kinematic (decimeter precicion)  6=estimated (dead reckoning 2.3 feature)    7=Manual input mode   8=Simulation mode
13 --> denotes number of satellites used in the coordinate
1.00 --> denotes the HDOP (horizontal dilution of position)
495.144,M --> altitud, meters, above sea level 
29.200,M --> Height of geoid separation (geoid means ocean leve) subtract this from the altitude of the antenna to arrive at the height above Ellipsoid
0.10 --> time in seconds since last DGPS update
0000 --> DGPS station ID number
*40 --> checksum data 
