-- ============================================================
-- V1__init_schema.sql
-- Portfolio Backend - Initial Database Schema
-- ============================================================

-- ===== EXTENSIONS =====
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ===== ENUM TYPES =====
CREATE TYPE skill_category AS ENUM ('HARD', 'SOFT');
CREATE TYPE proficiency_level AS ENUM ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT');
CREATE TYPE language_proficiency AS ENUM ('BASIC', 'CONVERSATIONAL', 'PROFESSIONAL', 'FLUENT', 'NATIVE');

-- ===== USERS =====
CREATE TABLE users (
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username     VARCHAR(50)  NOT NULL UNIQUE,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    github_id    VARCHAR(100) UNIQUE,
    avatar_url   TEXT,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email    ON users (email);
CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_github   ON users (github_id);

-- ===== REFRESH TOKENS =====
CREATE TABLE refresh_tokens (
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id    UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    token      TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked    BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_refresh_tokens_token   ON refresh_tokens (token);
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);

-- ===== PERSONAL INFO =====
CREATE TABLE personal_info (
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id           UUID NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    full_name         VARCHAR(255),
    professional_title VARCHAR(255),
    phone             VARCHAR(50),
    email             VARCHAR(255),
    domicile          VARCHAR(255),
    linkedin_url      TEXT,
    github_url        TEXT,
    website_url       TEXT,
    other_links       JSONB DEFAULT '{}'::jsonb,
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_personal_info_user_id ON personal_info (user_id);

-- ===== PROFESSIONAL SUMMARY =====
CREATE TABLE professional_summary (
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id    UUID NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    content    TEXT,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_professional_summary_user_id ON professional_summary (user_id);

-- ===== WORK EXPERIENCES =====
CREATE TABLE work_experiences (
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id            UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    company_name       VARCHAR(255) NOT NULL,
    location           VARCHAR(255),
    position           VARCHAR(255) NOT NULL,
    start_date         DATE NOT NULL,
    end_date           DATE,
    is_current         BOOLEAN NOT NULL DEFAULT FALSE,
    description_points TEXT[] DEFAULT '{}',
    order_index        INTEGER NOT NULL DEFAULT 0,
    created_at         TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_work_experiences_user_id ON work_experiences (user_id);

-- ===== EDUCATIONS =====
CREATE TABLE educations (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    institution VARCHAR(255) NOT NULL,
    degree      VARCHAR(255),
    major       VARCHAR(255),
    start_year  INTEGER,
    end_year    INTEGER,
    gpa         DECIMAL(3, 2),
    order_index INTEGER NOT NULL DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_educations_user_id ON educations (user_id);

-- ===== SKILLS =====
CREATE TABLE skills (
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id           UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name              VARCHAR(100) NOT NULL,
    category          skill_category NOT NULL,
    proficiency_level proficiency_level NOT NULL,
    order_index       INTEGER NOT NULL DEFAULT 0,
    created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_skills_user_id ON skills (user_id);

-- ===== CERTIFICATIONS =====
CREATE TABLE certifications (
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id        UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name           VARCHAR(255) NOT NULL,
    issuer         VARCHAR(255) NOT NULL,
    issue_date     DATE,
    expiry_date    DATE,
    credential_url TEXT,
    order_index    INTEGER NOT NULL DEFAULT 0,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_certifications_user_id ON certifications (user_id);

-- ===== PROJECTS =====
CREATE TABLE projects (
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id        UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name           VARCHAR(255) NOT NULL,
    description    TEXT,
    tech_stack     TEXT[] DEFAULT '{}',
    project_url    TEXT,
    repo_url       TEXT,
    thumbnail_url  TEXT,
    start_date     DATE,
    end_date       DATE,
    order_index    INTEGER NOT NULL DEFAULT 0,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_projects_user_id ON projects (user_id);

-- ===== AWARDS =====
CREATE TABLE awards (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    title       VARCHAR(255) NOT NULL,
    issuer      VARCHAR(255),
    date        DATE,
    description TEXT,
    order_index INTEGER NOT NULL DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_awards_user_id ON awards (user_id);

-- ===== LANGUAGES =====
CREATE TABLE languages (
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id      UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    language_name VARCHAR(100) NOT NULL,
    proficiency  language_proficiency NOT NULL,
    order_index  INTEGER NOT NULL DEFAULT 0,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_languages_user_id ON languages (user_id);

-- ===== AUTO-UPDATE TRIGGERS =====
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_work_experiences_updated_at
    BEFORE UPDATE ON work_experiences
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_educations_updated_at
    BEFORE UPDATE ON educations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_skills_updated_at
    BEFORE UPDATE ON skills
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_certifications_updated_at
    BEFORE UPDATE ON certifications
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_projects_updated_at
    BEFORE UPDATE ON projects
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_awards_updated_at
    BEFORE UPDATE ON awards
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trg_languages_updated_at
    BEFORE UPDATE ON languages
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
