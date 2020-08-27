-- создаем хранимую процедуру для расчета количества комментариев статьи по ее id
CREATE OR REPLACE FUNCTION get_count_comments(IN art_id bigint) RETURNS bigint
    AS 'SELECT coalesce(count(id),0)
        FROM comments
        WHERE article_id=$1;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

-- триггерная функция, вызываемая триггером после (insert, update, delete) таблицы comments
-- и пересчитывающая вычисляемое поле count_comments в таблице articles
CREATE OR REPLACE FUNCTION update_count_comments() RETURNS TRIGGER AS $update_comments_count_trig$
DECLARE count_comm bigint;
BEGIN
    IF (TG_OP = 'INSERT') THEN
        count_comm = get_count_comments(NEW.article_id);
        UPDATE articles SET count_comments = count_comm WHERE id = NEW.article_id;
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        count_comm = get_count_comments(OLD.article_id);
        UPDATE articles SET count_comments = count_comm WHERE id = OLD.article_id;
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        count_comm = get_count_comments(NEW.article_id);
        UPDATE articles SET count_comments = count_comm WHERE id = NEW.article_id;
        IF (NEW.article_id!=OLD.article_id) THEN
            count_comm = get_count_comments(OLD.article_id);
            UPDATE articles SET count_comments = count_comm WHERE id = OLD.article_id;
        END IF;
        RETURN NEW;
    END IF;
END
$update_comments_count_trig$ LANGUAGE plpgsql;

-- триггер, срабатывающий на изменение таблицы article_likes_ratings и вызывающий функцию пересчета
-- вычисляемых полей count_likes, count_dislikes, rating в таблице articles
CREATE TRIGGER update_comments_count_trig
    AFTER INSERT OR UPDATE OR DELETE ON comments
    FOR EACH ROW EXECUTE PROCEDURE update_count_comments();
