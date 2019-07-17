import math
import sys

file1 = open("/home/pi/Desktop/practice/gps_data.txt")
hello = file1.readlines()
file1.close()

if len(hello)<2:
    print("empty")
    sys.exit()

lat_1=float(hello[len(hello)-2].split(";")[0])
lon_1= float(hello[len(hello)-2].split(";")[1])
time1 = hello[len(hello)-2].split(";")[2]

time1 = time1.split("T")[1]
time1 = time1.split(".")[0]

lat_2 = float(hello[len(hello)-1].split(";")[0])
lon_2 = float(hello[len(hello)-1].split(";")[1])
time2 = hello[len(hello)-1].split(";")[2]

time2 = time2.split("T")[1]
time2 = time2.split(".")[0]

#print(time1)
#print(time2)

def time_diff(tim1,tim2):
    sec1 = (int(tim1.split(":")[0])*3600)+(int(tim1.split(":")[1])*60)+int(tim1.split(":")[2])
    sec2 = (int(tim2.split(":")[0])*3600)+(int(tim2.split(":")[1])*60)+int(tim2.split(":")[2])
    return sec2-sec1

def distance_on_geoid(lat1,lon1,lat2,lon2):
    try:
        lat1 = lat1*math.pi /180.0
	lon1 =lon1*math.pi / 180.0
	lat2 = lat2*math.pi /180.0
	lon2 = lon2*math.pi /180.0
	
	r = 6378100

	rho1=r*math.cos(lat1)
	z1=r*math.sin(lat1)
	x1 =rho1*math.cos(lon1)
	y1 = rho1*math.sin(lon1)

	rho2 = r*math.cos(lat2)
	z2 = r*math.sin(lat2)
	x2 = rho2*math.cos(lon2)
	y2 = rho2*math.sin(lon2)

	dot = (x1*x2 + y1*y2 + z1*z2)
	cos_theta = dot/(r*r)

	theta = math.acos(cos_theta)
	return r*theta
    except:
        print("")

def calculate_speed(dist,tim):
    speed = float(dist/tim)
    speed = (speed*3600)/1000
    return speed

speed_observed = calculate_speed(distance_on_geoid(lat_1,lon_1,lat_2,lon_2),time_diff(time1,time2))



#print(distance_on_geoid(lat_1,lon_1,lat_2,lon_2))
#print(speed_observed)



#print(time_diff(time1,time2))
print(str(lat_1)+";"+str(lon_1)+";"+time1+";"+str(lat_2)+";"+str(lon_2)+";"+time2+";"+str(speed_observed))
