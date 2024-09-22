-- Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT model,
	fare_conditions,
	COUNT(seat_no) seats_count
FROM aircrafts
JOIN seats ON aircrafts.aircraft_code = seats.aircraft_code
GROUP BY model,
	fare_conditions
ORDER  BY model DESC

-- Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT model,
	COUNT(seat_no) seat_count
FROM aircrafts
JOIN seats ON aircrafts.aircraft_code = seats.aircraft_code
GROUP BY model
ORDER  BY seat_count DESC
LIMIT 3

-- Вывести код, модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
SELECT aircrafts.aircraft_code,
	model,
	seat_no
FROM aircrafts
JOIN seats ON aircrafts.aircraft_code = seats.aircraft_code
WHERE aircrafts.aircraft_code = '321'
	AND fare_conditions != 'Economy'
ORDER BY seat_no

-- Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
SELECT airports_data.airport_code,
	airports_data.airport_name,
	airports_data.city
FROM airports_data
JOIN
	(SELECT city,
			COUNT(*)
		FROM airports_data
		GROUP BY city
		HAVING COUNT(*) > 1) AS a_c ON airports_data.city = a_c.city

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
SELECT *
FROM flights
JOIN
	(SELECT airports_data.airport_code
		FROM airports_data
		WHERE CITY = '{  "en": "Moscow",  "ru": "Москва"}') AS mt ON flights.arrival_airport = mt.airport_code
JOIN
	(SELECT airports_data.airport_code
		FROM airports_data
		WHERE city = '{  "en": "Yekaterinburg",  "ru": "Екатеринбург"}') AS ct ON flights.departure_airport = ct.airport_code
WHERE flights.status != 'Arrived'
ORDER BY flights.scheduled_departure
LIMIT 1

-- Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)
SELECT *
FROM (
							(SELECT *
								FROM ticket_flights
								ORDER BY amount ASC
								LIMIT 1)
						UNION
							(SELECT *
								FROM ticket_flights
								ORDER BY amount DESC
								LIMIT 1)) un
JOIN ticket_flights ON un.ticket_no = ticket_flights.ticket_no
AND un.flight_id = ticket_flights.flight_id

-- Вывести информацию о вылете с наибольшей суммарной стоимостью билетов
SELECT *
FROM
	(SELECT flight_id
		FROM ticket_flights
		GROUP BY flight_id
		HAVING SUM(amount) =
			(SELECT MAX(MAX.sum)
				FROM
					(SELECT flight_id,
							SUM(amount)
						FROM ticket_flights
						GROUP BY flight_id) MAX)) m_p
JOIN flights ON flights.flight_id = m_p.flight_id

-- Найти модель самолета, принесшую наибольшую прибыль (наибольшая суммарная стоимость билетов). Вывести код модели, информацию о модели и общую стоимость
SELECT a.aircraft_code,
	a.model,
	SUM(amount)
FROM aircrafts_data a
JOIN flights ON a.aircraft_code = flights.aircraft_code
JOIN ticket_flights ON ticket_flights.flight_id = flights.flight_id
GROUP BY a.aircraft_code
HAVING SUM(amount) =
	(SELECT MAX(SUM)
		FROM
			(SELECT b.aircraft_code,
					SUM(amount)
				FROM aircrafts_data b
				JOIN flights ON b.aircraft_code = flights.aircraft_code
				JOIN ticket_flights ON ticket_flights.flight_id = flights.flight_id
				GROUP BY B.aircraft_code) c)

-- Найти самый частый аэропорт назначения для каждой модели самолета. Вывести количество вылетов, информацию о модели самолета, аэропорт назначения, город
WITH t1 AS
	(SELECT ad.aircraft_code,
			adt.city,
			ad.model,
			f.arrival_airport,
			COUNT(F.arrival_airport) count_arr_ap
		FROM aircrafts_data ad
		JOIN flights f ON AD.aircraft_code = f.aircraft_code
		JOIN airports_data adt ON adt.airport_code = f.arrival_airport
		GROUP BY ad.aircraft_code,
			f.arrival_airport,
			adt.airport_code)
SELECT wmc.model,
	wmc.max_arr_ap,
	t1.CITY,
	t1.arrival_airport
FROM
	(SELECT model,
			MAX(count_arr_ap) max_arr_ap
		FROM t1
		GROUP BY model) wmc
JOIN t1 ON wmc.model = t1.model
AND wmc.max_arr_ap = t1.count_arr_ap