import gmplot

file1=open("/home/pi/Desktop/practice/gps_data.txt","r")
hello=file1.readlines()
file1.close()

latitude=[]
longitude=[]

for a,i in enumerate(hello):
    
    
    i=i.strip()
    lat,lon,time=i.split(';')
    latitude.append(float(lat))
    longitude.append(float(lon))
    if a==(len(hello)-2):
        break

center = int(len(latitude)/2)
gmap1 = gmplot.GoogleMapPlotter(latitude[center],longitude[center], 17) 
gmap1.marker( latitude[0],longitude[0],title="Start point" )
 
gmap1.plot(latitude, longitude,  
           'blue', edge_width = 3.5) 
  
gmap1.draw("/home/pi/Desktop/practice/map13.html") 
print("map made")
  


