import gps
session = gps.gps("localhost","2947")
session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)
print("agent started")

while True:
    try:
        report = session.next()
        if report['class']=='TPV':
            if hasattr(report,'time'):
                print(report)
 		print(report.time)
		file1 = open("/home/pi/Desktop/practice/gps_data.txt","a")
		file1.write(str(report.lat)+";"+str(report.lon)+";"+str(report.time)+"\n")
		file1.close()
		quit()
    except StopIteration:
        session = None
