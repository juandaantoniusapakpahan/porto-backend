-- Convert PostgreSQL custom enum columns to VARCHAR so Hibernate @Enumerated(STRING) works correctly
ALTER TABLE skills ALTER COLUMN category TYPE VARCHAR(50) USING category::TEXT;
ALTER TABLE skills ALTER COLUMN proficiency_level TYPE VARCHAR(50) USING proficiency_level::TEXT;
ALTER TABLE languages ALTER COLUMN proficiency TYPE VARCHAR(50) USING proficiency::TEXT;

-- Drop the now-unused custom enum types
DROP TYPE IF EXISTS skill_category;
DROP TYPE IF EXISTS proficiency_level;
DROP TYPE IF EXISTS language_proficiency;
