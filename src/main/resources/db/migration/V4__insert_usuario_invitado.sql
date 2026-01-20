-- ============================================
-- Inserción de usuario invitado
-- ============================================

INSERT INTO users (
    username,
    email,
    password,
    rol,
    chat_id
)
VALUES (
    'invitado',
    'invitado@system.local',
    '',
    'INVITADO',
    NULL
);

