SELECT * FROM tbsdb.seat;
update tbsdb.seat set status = 'Available';
update tbsdb.seat set booking_id = null;
commit;
delete from tbsdb.booking;
commit;
DELETE FROM tbsdb.payment;
commit;
DELETE FROM tbsdb.pre_booking_lock;
commit;

SELECT * FROM tbsdb.payment;
SELECT * FROM tbsdb.pre_booking_lock;
SELECT * FROM tbsdb.booking;
SELECT * FROM tbsdb.seat;
