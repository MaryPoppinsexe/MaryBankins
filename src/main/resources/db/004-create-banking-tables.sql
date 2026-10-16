--liquibase formatted sql

--changeset you:5
CREATE TABLE account (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         account_number VARCHAR(20) NOT NULL UNIQUE,
                         user_id UUID NOT NULL,
                         balance NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         label VARCHAR(100) NOT NULL DEFAULT 'Account',
                         created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                         updated_at TIMESTAMP WITH TIME ZONE,
                         CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);
--rollback DROP TABLE account;

--changeset you:6
CREATE TABLE transaction (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             account_id UUID NOT NULL,
                             amount NUMERIC(19, 2) NOT NULL,
                             type VARCHAR(30) NOT NULL,
                             status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
                             related_transaction_id UUID,
                             description VARCHAR(255),
                             created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                             updated_at TIMESTAMP WITH TIME ZONE,
                             CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(id),
                             CONSTRAINT fk_related_transaction FOREIGN KEY (related_transaction_id) REFERENCES transaction(id)
);
--rollback DROP TABLE transaction;

--changeset you:7
CREATE INDEX idx_transaction_account_id ON transaction(account_id);
CREATE INDEX idx_transaction_created_at ON transaction(created_at DESC);
CREATE INDEX idx_account_user_id ON account(user_id);
--rollback DROP INDEX idx_transaction_account_id; DROP INDEX idx_transaction_created_at; DROP INDEX idx_account_user_id;