CREATE TABLE IF NOT EXISTS usuario_refresh_token (
    refresh_token_id CHAR(36) NOT NULL,
    usuario_id CHAR(36) NOT NULL,
    token_hash VARCHAR(64) NOT NULL,
    expires_at DATETIME(6) NOT NULL,
    revoked_at DATETIME(6) NULL,
    created_at DATETIME(6) NOT NULL,
    last_used_at DATETIME(6) NULL,
    user_agent VARCHAR(255) NULL,
    ip_address VARCHAR(64) NULL,
    PRIMARY KEY (refresh_token_id),
    UNIQUE KEY uq_refresh_token_hash (token_hash),
    KEY idx_refresh_token_usuario (usuario_id),
    KEY idx_refresh_token_expira (expires_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
