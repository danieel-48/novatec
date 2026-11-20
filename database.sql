create database banco


CREATE TABLE product (
    product_id VARCHAR(6) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(10) NOT NULL,

    CONSTRAINT chk_product_id_format
        CHECK (product_id ~ '^[0-9]{6}$'),

    CONSTRAINT chk_product_type
        CHECK (type IN ('DEBIT', 'CREDIT'))
);

CREATE TABLE card (
    card_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    product_id VARCHAR(6) NOT NULL,
    card_number VARCHAR(16) NOT NULL,
    holder_name VARCHAR(150) NOT NULL,

    issue_at DATE NOT NULL,
    expires_at DATE NOT NULL,

    status VARCHAR(15) NOT NULL,

    blocked_at TIMESTAMP NULL,
    blocked_reason VARCHAR(255) NULL,

    balance NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',

    CONSTRAINT fk_card_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT uq_card_number
        UNIQUE (card_number),

    CONSTRAINT chk_card_number_format
        CHECK (card_number ~ '^[0-9]{16}$'),

    CONSTRAINT chk_card_status
        CHECK (status IN ('ISSUED', 'ACTIVE', 'BLOCKED')),

    CONSTRAINT chk_card_currency
        CHECK (currency = 'USD'),

    CONSTRAINT chk_card_balance_non_negative
        CHECK (balance >= 0.00),

    CONSTRAINT chk_card_dates
        CHECK (expires_at > issue_at)
);


INSERT INTO product (product_id, name, type)VALUES ('123456', 'Tarjeta Oro', 'DEBIT');

insert INTO card (product_id, card_number, holder_name, issue_at, expires_at, status, balance, currency) VALUES ('123456', '1234567890123456', 'Daniel Ariza', CURRENT_DATE, CURRENT_DATE + INTERVAL '4 years', 'ACTIVE', 150000.00, 'USD');

