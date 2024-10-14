CREATE TABLE IF NOT EXISTS "app_user"
(
    "id" bigserial PRIMARY KEY,
    "nickname" character varying(255) UNIQUE,
    "name" character varying(255),
    "email" character varying(255)
);

CREATE TABLE IF NOT EXISTS "category"
(
    "id" bigserial PRIMARY KEY,
    "name" character varying(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS "channel"
(
    "id" bigserial PRIMARY KEY,
    "name" character varying(255),
    "description" character varying(255),
    "author_id" bigserial,
    "created_at" timestamp without time zone NOT NULL DEFAULT now(),
    "main_language" smallint,
    "base64image" character varying(255),
    "category_id" bigserial,
    CONSTRAINT "channel_category" FOREIGN KEY (category_id)
        REFERENCES "category" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "channel_app_user" FOREIGN KEY (author_id)
        REFERENCES "app_user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "channel_main_language_check" CHECK ("main_language" >= 0 AND "main_language" <= 1)
);

CREATE TABLE IF NOT EXISTS "channel_subscribers"
(
    "subscribers_id" bigserial,
    "subscriptions_id" bigserial,
    CONSTRAINT "channel_subscribers_pkey" PRIMARY KEY (subscribers_id, subscriptions_id),
    CONSTRAINT "subscrib_channel" FOREIGN KEY (subscriptions_id)
        REFERENCES "channel" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "subscrib_app_user" FOREIGN KEY (subscribers_id)
        REFERENCES "app_user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)