ALTER TABLE orders
    ADD COLUMN shipping_address TEXT;

UPDATE orders
SET shipping_address = 'TEMP_ADDRESS'
WHERE shipping_address IS NULL;

ALTER TABLE orders
    ALTER COLUMN shipping_address SET NOT NULL;
