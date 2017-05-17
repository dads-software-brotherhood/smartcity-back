mongoimport --db smartcity --collection alert   	--drop --file alert_file.json
mongoimport --db smartcity --collection country 	--drop --file country_file.json
mongoimport --db smartcity --collection locality 	--drop --file locality_file.json
mongoimport --db smartcity --collection notification 	--drop --file notification_file.json
mongoimport --db smartcity --collection region  	--drop --file region_file.json
mongoimport --db smartcity --collection vehicleType	--drop --file vehicle_type_file.json
mongoimport --db smartcity --collection publicTransportFuelType	--drop --file public_transport_fuel_type_file.json
mongoimport --db smartcity --collection agency	--drop --file agency_file.json
