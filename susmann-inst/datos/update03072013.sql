
CREATE TABLE vendedor
(
  id bigint NOT NULL,
  codigo character varying(255) NOT NULL,
  eliminado boolean NOT NULL,
  nombre character varying(255) NOT NULL,
  CONSTRAINT vendedor_pkey PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE vendedor OWNER TO postgres;
ALTER TABLE Venta
ADD COLUMN vendedor_id bigint,
ADD CONSTRAINT fk4eb7a2c21caf2d8 FOREIGN KEY (vendedor_id)
      REFERENCES vendedor (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE Venta
ADD COLUMN numerocaja integer NULL;
UPDATE Venta SET numerocaja=4;
ALTER TABLE Venta ALTER COLUMN numerocaja SET NOT NULL;
	  
	  