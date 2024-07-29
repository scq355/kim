-- 创建数据库
CREATE DATABASE db_hive1;

CREATE DATABASE db_hive2 LOCATION '/db_hive2';

CREATE DATABASE db_hive3 WITH DBPROPERTIES ('create_date' = '2024-07-20');

-- 查看数据库
SHOW DATABASES;

SHOW DATABASES LIKE 'db_*';

DESC DATABASE db_hive2;

DESCRIBE DATABASE EXTENDED db_hive3;

-- 修改数据库
ALTER DATABASE db_hive3 SET DBPROPERTIES ('create_date' = '2024-07-19');

-- 删除数据库
DROP DATABASE IF EXISTS db_hive1 CASCADE;

-- 切换数据库
USE db_hive1;

SELECT 1L + 1;

SELECT '1' + 1;

SELECT cast('111' AS INT);

USE default;

SHOW CREATE TABLE student;


-- 内部表
CREATE TABLE IF NOT EXISTS students(
                                       id INT COMMENT 'id',
                                       name STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
    LOCATION '/hive/student';

-- student.txt
-- 1001	student1
-- 1002	student2
-- 1003	student3
-- 1004	student4
-- 1005	student5
-- 1006	student6

-- hadoop fs -put /opt/module/datas/student.txt /hive/student

SELECT * FROM students;

DROP TABLE students;

-- 外部表
CREATE EXTERNAL TABLE IF NOT EXISTS students(
    id INT COMMENT 'id',
    name STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
LOCATION '/hive/student';

SELECT * FROM students;

DROP TABLE students;


CREATE TABLE teacher (
                         name STRING,
                         friends ARRAY<STRING>,
                         students MAP<STRING, INT>,
                         address STRUCT<city:STRING, street:STRING, postal_code:INT>
) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.JsonSerDe'
LOCATION '/hive/teacher';

SELECT friends[0], students['xiaohaihai'], address.city FROM teacher;

CREATE TABLE teacher1 AS SELECT * FROM teacher;
SELECT * FROM teacher1;

CREATE TABLE teacher2 LIKE teacher;
SELECT * FROM teacher2;

-- 查看表
SHOW TABLES LIKE 'stu*';
SHOW TABLES IN db_hive1 LIKE 'stu*';

DESCRIBE default.teacher;
DESCRIBE EXTENDED default.teacher;
DESCRIBE FORMATTED default.teacher;


-- 修改表
ALTER TABLE student RENAME TO stu;
SELECT * FROM stu;

ALTER TABLE stu ADD COLUMNS (gender STRING);

SET hive.metastore.disallow.incompatible.col.type.changes=false;

ALTER TABLE stu CHANGE COLUMN gender gender INT AFTER id;

ALTER TABLE stu REPLACE COLUMNS (id INT, name STRING);


-- load
CREATE TABLE student(
                        id INT,
                        name STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/student.txt' INTO TABLE student;
LOAD DATA LOCAL INPATH '/opt/module/datas/student.txt' OVERWRITE INTO TABLE student;

LOAD DATA INPATH '/student.txt' OVERWRITE INTO TABLE student;

SELECT * FROM student;


-- insert
CREATE TABLE student1(
                         id INT,
                         name STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

INSERT OVERWRITE TABLE student1
SELECT * FROM student;

INSERT INTO student1
VALUES (1, 'wangwu'), (2, 'zhaoliu');

INSERT OVERWRITE LOCAL DIRECTORY '/opt/module/datas/student' ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.JsonSerDe'
SELECT id, name FROM student;

-- im/export
EXPORT TABLE default.student TO '/opt/module/datas/export_student';
IMPORT TABLE default.student2 FROM '/opt/module/datas/export_student';

SELECT * FROM student2;

CREATE TABLE IF NOT EXISTS dept(
                                   deptno INT COMMENT '部门编号',
                                   dname STRING COMMENT '部门名称',
                                   loc INT COMMENT '部门位置'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

CREATE TABLE IF NOT EXISTS emp (
                                   empno INT COMMENT '员工编号',
                                   ename STRING COMMENT '员工姓名',
                                   job STRING COMMENT '员工岗位',
                                   sal DOUBLE COMMENT '员工薪资',
                                   deptno INT COMMENT '部门编号'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

CREATE TABLE IF NOT EXISTS location (
                                        loc INT COMMENT '部门位置ID',
                                        loc_name STRING COMMENT '部门位置'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/location.txt' INTO TABLE location;

LOAD DATA LOCAL INPATH '/opt/module/datas/dept.txt' INTO TABLE dept;
SELECT * FROM dept;

LOAD DATA LOCAL INPATH '/opt/module/datas/emp.txt' INTO TABLE emp;
SELECT * FROM emp;

-- 查询
SELECT empno, ename FROM emp;

SELECT
    empno as emp_id,
    ename emp_name
FROM emp;


SELECT
    empno as emp_id,
    ename emp_name
FROM emp
         LIMIT 2, 3;

SELECT
    *
FROM emp
WHERE sal > 1000;

SELECT
    *
FROM emp
WHERE sal BETWEEN 500 AND 1000;

SELECT
    *
FROM emp
WHERE job IN ('研发', '销售');

SELECT
    *
FROM emp
WHERE ename NOT LIKE '张%';

SELECT
    *
FROM emp
WHERE ename RLIKE '张' OR ename REGEXP '赵';

-- 本地模式
SET mapreduce.framework.name = local;

SELECT count(*) FROM emp;

SELECT count(empno) FROM emp;
SELECT count(job) FROM emp;

SELECT max(sal) FROM emp;
SELECT min(sal) FROM emp;
SELECT sum(sal) FROM emp;
SELECT avg(sal) FROM emp;
SELECT sum(sal) / count(*) FROM emp;

SELECT job, count(*) FROM emp GROUP BY job;


SELECT *
from
    (
        SELECT
            job,
            count(*) as cnt
        FROM emp GROUP BY job
    ) t
WHERE t.cnt >= 2;

SELECT job, count(*) as cnt FROM emp GROUP BY job HAVING cnt >= 2;

-- join
SELECT *
FROM emp JOIN dept ON emp.deptno = dept.deptno;

SELECT
    loc,
    count(*)
FROM emp e JOIN dept d ON e.deptno = d.deptno
GROUP BY d.loc;

-- 等值 不等值链接
SELECT
    *
FROM emp e JOIN dept d ON e.deptno = d.deptno;

SELECT
    *
FROM emp e JOIN dept d ON e.deptno != d.deptno;


-- 内连接
SELECT
    e.empno,
    e.ename,
    d.deptno
FROM emp e INNER JOIN dept d ON e.deptno = d.deptno;


-- 左外连接
SELECT
    e.empno,
    e.ename,
    d.deptno
FROM emp e LEFT OUTER JOIN dept d ON e.deptno = d.deptno;


-- 右外连接
SELECT
    e.empno,
    e.ename,
    d.deptno
FROM emp e RIGHT JOIN dept d ON e.deptno = d.deptno;


-- 满外连接
SELECT
    e.empno,
    e.ename,
    d.deptno
FROM emp e FULL JOIN dept d ON e.deptno = d.deptno;

SELECT * FROM location;

-- 多表连接
SELECT
    e.ename,
    d.dname,
    l.loc_name
FROM emp e JOIN dept d ON e.deptno = d.deptno
           JOIN location l ON d.loc = l.loc;


SELECT * FROM emp WHERE deptno = 30
UNION
SELECT * FROM emp WHERE deptno = 40;


SELECT * FROM emp WHERE deptno = 30
UNION ALL
SELECT * FROM emp WHERE deptno = 30;


SELECT * FROM emp ORDER BY sal DESC LIMIT 3;

-- sortby 分区内有序

SET mapreduce.job.reduces=3;

SELECT
    *
FROM emp
         SORT BY deptno DESC;


INSERT OVERWRITE LOCAL DIRECTORY '/opt/module/datas/sortby-result'
SELECT
    *
FROM emp
         SORT BY deptno DESC;


INSERT OVERWRITE LOCAL DIRECTORY '/opt/module/datas/distribute-result'
SELECT
    *
FROM emp
         DISTRIBUTE BY deptno
SORT BY sal DESC;


CREATE DATABASE hive_practive;


USE hive_practive;

-- 创建学生表
DROP TABLE IF EXISTS student;
create table if not exists student_info(
                                           stu_id string COMMENT '学生id',
                                           stu_name string COMMENT '学生姓名',
                                           birthday string COMMENT '出生日期',
                                           sex string COMMENT '性别'
)
    row format delimited fields terminated by ','
    stored as textfile;

-- 创建课程表
DROP TABLE IF EXISTS course;
create table if not exists course_info(
                                          course_id string COMMENT '课程id',
                                          course_name string COMMENT '课程名',
                                          tea_id string COMMENT '任课老师id'
)
    row format delimited fields terminated by ','
    stored as textfile;

-- 创建老师表
DROP TABLE IF EXISTS teacher;
create table if not exists teacher_info(
                                           tea_id string COMMENT '老师id',
                                           tea_name string COMMENT '老师姓名'
)
    row format delimited fields terminated by ','
    stored as textfile;

-- 创建分数表
DROP TABLE IF EXISTS score;
create table if not exists score_info(
                                         stu_id string COMMENT '学生id',
                                         course_id string COMMENT '课程id',
                                         score int COMMENT '成绩'
)
    row format delimited fields terminated by ','
    stored as textfile;


LOAD DATA LOCAL INPATH '/opt/module/datas/practice/student_info.txt' INTO TABLE student_info;
SELECT * FROM student_info;
LOAD DATA LOCAL INPATH '/opt/module/datas/practice/course_info.txt' INTO TABLE course_info;
SELECT * FROM course_info;
LOAD DATA LOCAL INPATH '/opt/module/datas/practice/teacher_info.txt' INTO TABLE teacher_info;
SELECT * FROM teacher_info;
LOAD DATA LOCAL INPATH '/opt/module/datas/practice/score_info.txt' INTO TABLE score_info;
SELECT * FROM score_info;

-- 姓名带冰
SELECT * FROM student_info WHERE stu_name LIKE '%冰%';
SHOW TABLES LIKE '*student*';

-- 课程编号04 分数小于60 课程信息，分数降序排列
SELECT *
FROM score_info WHERE course_id = '04' AND score < 60
ORDER BY score DESC;

-- 数学成绩不及格的学生和对应的成绩，按学号升序排序
SELECT *
FROM score_info LEFT JOIN course_info ci ON score_info.course_id = ci.course_id
WHERE course_name = '数学' AND score < 60
ORDER BY stu_id;

-- 02课程总成绩
SELECT
    '02' as course_id,
    sum(score)
FROM score_info
WHERE course_id = '02';

-- 各科成绩最高最低分，课程 最高 最低
SELECT
    course_id,
    max(score),
    min(score)
FROM score_info
GROUP BY course_id;

-- 每门课程有多少学生参加考试
SELECT
    course_id,
    count(stu_id)
FROM score_info
GROUP BY course_id;

-- 显示语文数学英语三科成绩，没有成绩的输出为0，按学生有效平均成绩降序显示

SELECT
    t4.stu_id,
    nvl(t1.score, 0),
    nvl(t2.score, 0),
    nvl(t3.score, 0),
    t4.cnt,
    t4.avg_score
FROM
    (
        SELECT
            stu_id,
            score
        FROM score_info
        WHERE course_id = '01'
    ) t1
        FULL OUTER JOIN
    (
        SELECT
            stu_id,
            score
        FROM score_info
        WHERE course_id = '02'
    ) t2 on t1.stu_id = t2.stu_id
        FULL OUTER JOIN
    (
        SELECT
            stu_id,
            score
        FROM score_info
        WHERE course_id = '03'
    ) t3
    ON nvl(t1.stu_id, t2.stu_id) = t3.stu_id
        RIGHT JOIN
    (
        SELECT
            stu_id,
            count(*) AS cnt,
            avg(score) AS avg_score
        FROM score_info
        GROUP BY stu_id
    ) t4 ON t4.stu_id = coalesce(t1.stu_id, t2.stu_id, t3.stu_id);


-- 函数
SHOW FUNCTIONS;
SHOW FUNCTIONS LIKE '*string*';
DESC FUNCTION EXTENDED substring;

CREATE DATABASE functions;

USE functions;

SELECT 10 + 3;
SELECT 10 - 3;
SELECT 10 * 3;
SELECT 10 / 3;
SELECT 10 % 3;
SELECT 10 & 3;
SELECT 10 | 3;
SELECT 10 ^ 3;

SELECT round(4.5);
SELECT round(-1.5);
SELECT `floor`(4.5);
SELECT `floor`(-4.5);
SELECT ceil(4.1);
SELECT ceil(-4.1);

SELECT substring("1234567890", 2);
SELECT substring("1234567890", 2, 3);
SELECT substring("1234567890", -4);
SELECT substring("1234567890", -4, 2);

SELECT replace("1234567890", '45', 'f');
SELECT replace('123456789045', '45', 'f');

SELECT REGEXP_REPLACE('12343-bbb-65754538-ddd-eee', '[0-9]+', '*');
SELECT REGEXP_REPLACE('12343-bbb-65754538-ddd-eee', '\\d{1,}', '*');

SELECT 'string' LIKE '%str%';
SELECT 'string' REGEXP '.*str.*';

SELECT repeat('str ', 4);

SELECT split("192.168.124.124", "\\.");

SELECT nvl(3, 0);
SELECT nvl(NULL, 0);
SELECT nvl(NULL, NULL);

SELECT concat('12', '23', '34');
SELECT 'aa' || 'bb' || 'cc';
SELECT concat_ws('-', 'aa', 'bb', 'cc');
SELECT `array`('aa', 'bb', 'cc');
SELECT concat_ws('-', `array`('aa', 'bb', 'cc'));

SELECT get_json_object('[{"name":"name1","sex":"男","age":23},{"name":"name2","sex":"男","age":45}]', "$[1].age");

SELECT unix_timestamp();
SELECT unix_timestamp('2024/07/23 13:00:00', 'yyyy/MM/dd HH:mm:ss');
SELECT from_unixtime(1721717202, 'yyyy-MM-dd HH:mm:ss');
SELECT from_utc_timestamp(1721717202000, 'GMT+8');
SELECT date_format(from_utc_timestamp(1721717202000, 'GMT+8'), 'yyyy-MM-dd HH:mm:ss');
SELECT `current_date`();
SELECT `current_timestamp`();

SELECT month('2024-07-23 02:56:38');
SELECT day('2024-07-23 02:56:38');
SELECT year('2024-07-23 02:56:38');
SELECT hour('2024-07-23 02:56:38');
SELECT minute('2024-07-23 02:56:38');
SELECT second('2024-07-23 02:56:38');

SELECT datediff('2024-09-17', '2024-07-24');
SELECT date_add('2024-09-17', 5);
SELECT date_sub('2024-09-17', 5);

SELECT
    stu_id,
    course_id,
    CASE
        WHEN score >= 90 THEN 'A'
        WHEN score >= 80 AND score < 90 THEN 'B'
        WHEN score >= 70 THEN 'C'
        WHEN score >= 70 THEN 'D'
        ELSE 'E'
        END
FROM hive_practive.score_info;

SELECT
    stu_id,
    course_id,
    CASE score
        WHEN 90 THEN 'A'
        WHEN 80 THEN 'B'
        WHEN 70 THEN 'C'
        WHEN 70 THEN 'D'
        ELSE 'E'
        END
FROM hive_practive.score_info;

SELECT `if`(10 / 5 == 0, '整除', '除余');

SELECT `array`('a', 'b', 'c', 'd');
SELECT array_contains(`array`('a', 'b', 'c'), 'c');
SELECT sort_array(`array`(3,2,1));
SELECT size(`array`('a', 'b', 'c', 'd'));

SELECT `map`('abc',1, 'def',3);
SELECT map_keys(map('abc',1, 'def',3));
SELECT map_values(map('abc',1, 'def',1));

SELECT struct('name', 'sex', 'age');
SELECT named_struct('name', 'xiaohai', 'sex','男', 'age','12');


USE hive_practive;

CREATE TABLE IF NOT EXISTS employee (
                                        name STRING COMMENT '姓名',
                                        sex STRING COMMENT '性别',
                                        birthday STRING COMMENT '出生年月',
                                        hiredate STRING COMMENT '入职日期',
                                        job STRING COMMENT '岗位',
                                        salary DOUBLE COMMENT '薪资',
                                        bonus DOUBLE COMMENT '奖金',
                                        friends ARRAY<STRING> COMMENT '朋友',
                                        children MAP<STRING, INT> COMMENT '孩子'
);

INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('张无忌', '男', '1980/02/12', '2022/08/09', '销售', 3000, 12000, `array`('阿朱', '小昭'), `map`('张小五', 8, '张小计', 9));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('赵敏', '女', '1982/05/18', '2022/09/10', '行政', 9000, 2000, `array`('阿三', '阿四'), `map`('赵小敏', 8));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('宋青书', '男', '1981/03/15', '2022/04/09', '研发', 18000, 1000, `array`('王五', '赵六'), `map`('宋小青', 7, '宋小书', 8));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('周芷若', '女', '1981/03/17', '2022/04/10', '研发', 18000, 1000, `array`('王五', '赵六'), `map`('宋小青', 7, '宋小书', 8));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('郭靖', '男', '1985/03/11', '2022/07/19', '销售', 2000, 13000, `array`('南帝', '北丐'), `map`('郭芙', 5, '郭襄', 4));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('黄蓉', '女', '1982/12/13', '2022/06/11', '行政', 12000, NULL, `array`('东邪', '西毒'), `map`('郭芙', 5, '郭襄', 4));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('杨过', '男', '1988/01/30', '2022/08/13', '前台', 5000, NULL, `array`('郭靖', '黄蓉'), `map`('杨小过', 2));
INSERT INTO employee (name, sex, birthday, hiredate, job, salary, bonus, friends, children)
VALUES ('小龙女', '女', '1985/02/12', '2022/09/24', '前台', 6000, NULL, `array`('张三', '李四'), `map`('杨小过', 2));

SELECT * FROM employee;

-- 每个月份入职人数
SELECT
    count(*),
    year(replace(hiredate, '/', '-')),
    month(replace(hiredate, '/', '-'))
FROM employee
GROUP BY year(replace(hiredate, '/', '-')), month(replace(hiredate, '/', '-'));

SELECT
    count(*),
    substring(replace(hiredate, '/', '-'), 1, 7)
FROM employee
GROUP BY substring(replace(hiredate, '/', '-'), 1, 7);

-- 计算年龄
SELECT
    name,
    y || '年' || m || '月'
FROM
    (
        SELECT
            name,
            `floor`(num_month / 12) y,
            cast(round(num_month % 12) as INT) m
        FROM
            (
                SELECT
                    name,
                    months_between(`current_date`(), replace(birthday, '/', '-')) num_month
                FROM employee
            ) t
    ) t2;

-- 薪资奖金和排序
SELECT
    name,
    salary + nvl(bonus, 0) s,
    salary + `if`(bonus is NULL, 0, bonus)
FROM employee
ORDER BY s DESC;

-- 每个人有多少个朋友
SELECT
    name,
    size(friends)
FROM employee;

-- 每个人的孩子姓名
SELECT
    name,
    map_keys(children)
FROM employee;

-- 每个岗位男女各有多少员工
SELECT
    nvl(male.job, female.job),
    nvl(male.cnt, 0),
    nvl(female.cnt, 0)
FROM
    (
        SELECT
            job,
            count(*) cnt
        FROM employee
        WHERE sex = '男'
        GROUP BY job
    ) male
        FULL OUTER JOIN
    (
        SELECT
            job,
            count(*) cnt
        FROM employee
        WHERE sex = '女'
        GROUP BY job
    ) female on male.job = female.job;

SELECT
    job,
    sum(`if`(sex = '男', 1, 0)),
    sum(`if`(sex = '女', 1, 0)),
    count(`if`(sex = '男', 1, NULL)),
    count(`if`(sex = '女', 1, NULL))
FROM employee
GROUP BY job;

SELECT
    collect_list(job),
    collect_set(job)
FROM employee;

-- 每个月份入职人数姓名
SELECT
    substring(hiredate, 1, 7),
    count(*),
    collect_list(name)
FROM employee
GROUP BY substring(hiredate, 1, 7);


-- 同姓学生名单 统计同姓人数大于2的姓
SELECT
    substring(stu_name, 1, 1),
    count(*) cnt
FROM student_info
GROUP BY substring(stu_name, 1, 1)
HAVING cnt >= 2;

-- 语文数学英语三科成绩，没成绩输出0，算平均成绩降序排列
SELECT
    stu_id,
    sum(`if`(course_id='01', score, 0)),
    sum(`if`(course_id='02', score, 0)),
    sum(`if`(course_id='03', score, 0)),
    count(*),
    avg(score)
FROM score_info
GROUP BY stu_id;

-- 所有课程成绩均小于60的学生学号姓名
SELECT
    t2.stu_id,
    t2.stu_name,
    t1.max_score
FROM
    (
        SELECT
            stu_id,
            max(score) max_score
        FROM score_info
        GROUP BY stu_id
        HAVING max_score < 60
    ) t1 LEFT JOIN student_info t2 ON t1.stu_id = t2.stu_id;

-- 两门以上的课程不及格同学的学号以及平均成绩
SELECT
    stu_id,
    count(`if`(score < 60, 1, NULL)) cnt,
    avg(score)
FROM score_info
GROUP BY stu_id
HAVING cnt >= 2;

-- 学过'李体音‘老师教的所有课程的同学的学号姓名
SELECT
    *
FROM
    (
        SELECT
            stu_id,
            count(*) cnt
        FROM score_info sc
                 JOIN course_info ci ON sc.course_id = ci.course_id
                 JOIN teacher_info ti ON ci.tea_id = ti.tea_id
        WHERE tea_name = '李体音'
        GROUP BY stu_id
    ) t1
        JOIN
    (
        SELECT count(*) total
        FROM course_info ci
                 LEFT JOIN teacher_info ti ON ci.tea_id = ti.tea_id
        WHERE tea_name = '李体音'
    ) t2 WHERE t1.cnt = t2.total;

-- 学过'李体音‘老师教的任意一门课程的同学的学号姓名
SELECT
    DISTINCT stu_id
FROM score_info
WHERE course_id IN (
    SELECT course_id
    FROM course_info ci
             LEFT JOIN teacher_info ti ON ci.tea_id = ti.tea_id
    WHERE tea_name = '李体音'
);

-- 没学过'李体音‘老师教的任意一门课程的同学的学号姓名
SELECT
    stu_id,
    stu_name
FROM student_info
WHERE stu_id NOT IN
      (
          SELECT
              DISTINCT stu_id
          FROM score_info
          WHERE course_id NOT IN (
              SELECT course_id
              FROM course_info ci
                       LEFT JOIN teacher_info ti ON ci.tea_id = ti.tea_id
              WHERE tea_name = '李体音'
          )
      );

-- 至少有一门课跟学号‘001’的学生学的课程是相同的学生学号和姓名
SELECT
    DISTINCT stu_id
FROM score_info WHERE course_id IN (SELECT course_id
                                    FROM score_info
                                    WHERE stu_id = '001');

-- UDTF
SELECT explode(`array`('a', 'b', 'c')) as item;

SELECT explode(`map`('a',1, 'b',2 ,'c',3)) as (key,value);

SELECT posexplode(`array`('1', '2', '3')) as (pos, item);

SELECT inline(`array`(named_struct('id', 1, 'name', 'scq'), named_struct('id', 2, 'name', 'hive'))) as (id, name);

USE hive_practive;
CREATE TABLE person (
                        id INT,
                        name STRING,
                        hobbies ARRAY<STRING>
);

INSERT INTO person (id, name, hobbies)
VALUES (1, 'zs', `array`('reading', 'coding')),
       (2, 'ls', `array`('coding', 'running')),
       (3, 'ww', `array`('sleeping'));

SELECT
    id,
    name,
    hobbies,
    hobby
FROM person LATERAL VIEW explode(hobbies) tmp as hobby;

CREATE TABLE movie_info (
                            movie STRING COMMENT '电影名称',
                            category STRING COMMENT '电影分类'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';


INSERT INTO movie_info (movie, category)
VALUES ("《疑犯追踪》", "悬疑,动作,科幻,剧情"),
       ("《Lie to me》", "悬疑,警匪,动作,心理,剧情"),
       ("《战狼2》", "战争,动作,灾难");

-- 统计各分类的电影的数量
SELECT
    cate,
    count(*)
FROM
    (
        SELECT
            movie,
            split(category, ",") category
        FROM movie_info
    ) t1 LATERAL VIEW explode(category) tmp as cate
GROUP BY cate;

-- 窗口函数
CREATE TABLE order_info (
                            order_id INT,
                            user_id BIGINT,
                            order_date DATE,
                            amount BIGINT
)ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

INSERT INTO order_info (order_id, user_id, order_date, amount)
VALUES (1, 1001, '2022-01-01', 10),
       (2, 1001, '2022-01-02', 20),
       (3, 1001, '2022-01-03', 10),
       (4, 1002, '2022-01-04', 30),
       (5, 1002, '2022-01-05', 40),
       (6, 1002, '2022-01-06', 20);

-- 基于行
SELECT
    order_id,
    order_date,
    amount,
    sum(amount) OVER (ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) total_amount
FROM order_info;

-- 基于值
SELECT
    order_id,
    order_date,
    amount,
    sum(amount) OVER (ORDER BY order_date
        RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) total_amount
FROM order_info;

-- 带分区
SELECT
    order_id,
    order_date,
    amount,
    sum(amount) OVER (PARTITION BY user_id ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) total_amount
FROM order_info;

-- 跨行取值
SELECT
    order_id,
    user_id,
    order_date,
    amount,
    lag(order_date, 1, '1970-01-01') OVER (PARTITION BY user_id ORDER BY order_date) last_date,
        lead(order_date, 1, '9999-12-31') OVER (PARTITION BY user_id ORDER BY order_date) next_date
FROM order_info;


SELECT
    order_id,
    user_id,
    order_date,
    amount,
    first_value(order_date, FALSE) OVER (PARTITION BY user_id ORDER BY order_date) first_date,
        last_value(order_date, FALSE) OVER (PARTITION BY user_id ORDER BY order_date) last_date
FROM order_info;

-- 排名
CREATE TABLE score_rank (
                            stu_id INT,
                            course STRING,
                            score INT
);

INSERT INTO score_rank (stu_id, course, score)
VALUES (1, '语文', 99),
       (2, '语文', 98),
       (3, '语文', 95),
       (4, '数学', 100),
       (5, '数学', 100),
       (6, '数学', 99);

SELECT
    stu_id,
    course,
    score,
    rank() OVER (PARTITION BY course ORDER BY score DESC) rk,
        dense_rank() OVER (PARTITION BY course ORDER BY score DESC) dense_rk,
        row_number() OVER (PARTITION BY course ORDER BY score DESC) rn
FROM score_rank;


CREATE TABLE order_info_details (
                                    order_id STRING COMMENT '订单ID',
                                    user_id STRING COMMENT '用户ID',
                                    user_name STRING COMMENT '用户姓名',
                                    order_date STRING COMMENT '下单日期',
                                    order_amount INT COMMENT '订单金额'
);

INSERT OVERWRITE TABLE order_info_details
VALUES ('1', '1001', '小元', '2022-01-01', '10'),
       ('2', '1002', '小海', '2022-01-02', '15'),
       ('3', '1001', '小元', '2022-02-03', '23'),
       ('4', '1002', '小海', '2022-01-04', '29'),
       ('5', '1001', '小元', '2022-01-05', '46'),
       ('6', '1001', '小元', '2022-04-06', '42'),
       ('7', '1002', '小海', '2022-01-07', '50'),
       ('8', '1001', '小元', '2022-01-08', '50'),
       ('9', '1003', '小辉', '2022-04-08', '62'),
       ('10', '1003', '小辉', '2022-04-09', '62'),
       ('11', '1004', '小猛', '2022-05-10', '12'),
       ('12', '1003', '小辉', '2022-04-11', '75'),
       ('13', '1004', '小猛', '2022-06-12', '80'),
       ('14', '1003', '小辉', '2022-04-13', '94');


SELECT * FROM order_info_details;

-- 每个用户截至每次下单的累计下单总额
SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    sum(order_amount) OVER (PARTITION BY user_id ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
FROM order_info_details;

-- 每个用户截至每次下单的当月的累计下单总额
SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    sum(order_amount) OVER (PARTITION BY user_id, substring(order_date, 1, 7) ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
FROM order_info_details;

-- 每个用户截止每次下单距离上次下单间隔天数
SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    nvl(datediff(order_date, last_order_date), 0)
FROM
    (
        SELECT
            order_id,
            user_id,
            user_name,
            order_date,
            order_amount,
            lag(order_date, 1, NULL) OVER (PARTITION BY user_id ORDER BY order_date ASC) last_order_date
        FROM order_info_details
    ) t1;

SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    datediff(order_date, last_order_date)
FROM
    (
        SELECT
            order_id,
            user_id,
            user_name,
            order_date,
            order_amount,
            lag(order_date, 1, order_date) OVER (PARTITION BY user_id ORDER BY order_date ASC) last_order_date
        FROM order_info_details
    ) t1;

-- 所有下单记录及每个下单记录所在月份首末下单日期
SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    first_value(order_date, FALSE) OVER (PARTITION BY user_id, substring(order_date, 1, 7) ORDER BY order_date) first_order,
        last_value(order_date, FALSE) OVER (PARTITION BY user_id, substring(order_date, 1, 7) ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) last_order
FROM order_info_details

-- 每个用户所有下单记录按照定案的呢金额排名
SELECT
    order_id,
    user_id,
    user_name,
    order_date,
    order_amount,
    rank() OVER (PARTITION BY user_id ORDER BY order_amount DESC),
        dense_rank() OVER (PARTITION BY user_id ORDER BY order_amount DESC),
        row_number() OVER (PARTITION BY user_id ORDER BY order_amount DESC)
FROM order_info_details;


-- 分组topN
SELECT
    *
FROM
    (
        SELECT
            course_id,
            stu_id,
            score,
            rank() OVER (PARTITION BY course_id ORDER BY score DESC) rk
        FROM score_info
    )t1 WHERE rk <= 3;

