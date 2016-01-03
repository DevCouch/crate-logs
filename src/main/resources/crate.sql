CREATE TABLE logs (
	id INT,
  	level INT,
  	message String,
  	createDate Timestamp,
  	INDEX message_ft using fulltext(message)
);