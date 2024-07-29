SET mapreduce.framework.name = local;
USE hive_middle;
CREATE TABLE IF NOT EXISTS user_info (
                                         user_id STRING COMMENT '用户ID',
                                         gender STRING COMMENT '性别',
                                         birthday STRING COMMENT '生日'
) COMMENT '用户信息表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

USE hive_middle;
INSERT INTO user_info (user_id, gender, birthday)
VALUES ('101', '男', '1990-01-01'),
       ('102', '女', '1991-02-01'),
       ('103', '女', '1992-03-01'),
       ('104', '男', '1993-04-01');
USE hive_middle;

CREATE TABLE IF NOT EXISTS sku_info (
                                        sku_id STRING COMMENT '商品ID',
                                        name STRING COMMENT '商品名',
                                        category_id STRING COMMENT '所属分类id',
                                        from_date STRING COMMENT '上架日期',
                                        price DOUBLE COMMENT '商品单价'
) COMMENT '商品属性表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
USE hive_middle;

INSERT INTO sku_info (sku_id, name, category_id, from_date, price)
VALUES ('1', 'xiaomi 10', '1', '2020-01-01', 2000),
       ('6', '洗碗机', '2', '2020-02-01', 2000),
       ('9', '自行车', '3', '2020-01-01', 1000);
USE hive_middle;

CREATE TABLE IF NOT EXISTS category_info (
                                             category_id STRING COMMENT '分类id',
                                             category_name STRING COMMENT '分类名称'
) COMMENT '商品分类信息表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
USE hive_middle;

INSERT INTO category_info (category_id, category_name)
VALUES ('1', '数码'),
       ('2', '厨卫'),
       ('3', '户外');

USE hive_middle;

CREATE TABLE IF NOT EXISTS order_info (
                                          order_id STRING COMMENT '订单id',
                                          user_id STRING COMMENT '用户id',
                                          create_date STRING COMMENT '下单日期',
                                          total_amount DECIMAL(16, 2) COMMENT '订单总金额'
) COMMENT '订单表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
USE hive_middle;

INSERT INTO order_info (order_id, user_id, create_date, total_amount)
VALUES ('1', '101', '2021-09-30', 29000.00),
       ('10', '103', '2020-10-20', 28000.00);
USE hive_middle;

CREATE TABLE IF NOT EXISTS order_detail (
                                            order_detail_id STRING COMMENT '订单明细id',
                                            order_id STRING COMMENT '订单id',
                                            sku_id STRING COMMENT '商品id',
                                            create_date STRING COMMENT '下单日期',
                                            proce DECIMAL(9, 2) COMMENT '商品单价',
                                            sku_num INT COMMENT '商品件数'
) COMMENT '订单明细表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

USE hive_middle;

INSERT INTO order_detail (order_detail_id, order_id, sku_id, create_date, proce, sku_num)
VALUES ('1', '1', '1', '2021-09-30', 2000.00, 2),
       ('2', '1', '3', '2021-09-30', 5000.00, 5),
       ('22', '10', '4', '2020-10-20', 6000.00, 1),
       ('23', '10', '5', '2020-10-20', 500.00, 24),
       ('24', '10', '6', '2020-10-20', 2000.00, 5);
USE hive_middle;

CREATE TABLE IF NOT EXISTS user_login_detail (
                                                 user_id STRING COMMENT '用户id',
                                                 ip_address STRING COMMENT 'ip地址',
                                                 login_ts STRING COMMENT '登录时间',
                                                 logout_ts STRING COMMENT '登出时间'
) COMMENT '用户登录明细表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

USE hive_middle;

INSERT INTO user_login_detail (user_id, ip_address, login_ts, logout_ts)
VALUES ('101', '180.149.130.161', '2021-09-21 08:00:00', '2021-09-27 08:30:00'),
       ('102', '120.245.11.2', '2021-09-22 09:00:00', '2021-09-27 09:30:00'),
       ('103', '27.184.97.3', '2021-09-23 10:00:00', '2021-09-27 10:30:00');
USE hive_middle;

CREATE TABLE IF NOT EXISTS sku_price_modify_detail (
                                                       sku_id STRING COMMENT '商品id',
                                                       new_price DECIMAL(16, 2) COMMENT '更改后的价格',
                                                       change_date STRING COMMENT '变更日期'
) COMMENT '商品价格变更明细表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

USE hive_middle;

INSERT INTO sku_price_modify_detail (sku_id, new_price, change_date)
VALUES ('1', 1900.00, '2021-09-25'),
       ('1', 2000.00, '2021-09-26'),
       ('2', 80.00, '2021-09-29'),
       ('2', 10.00, '2021-09-30');
USE hive_middle;

CREATE TABLE IF NOT EXISTS delivery_info (
                                             delivery_id STRING COMMENT '配送单id',
                                             order_id STRING COMMENT '订单id',
                                             user_id STRING COMMENT '用户id',
                                             order_date STRING COMMENT '下单日期',
                                             custom_date STRING COMMENT '期望配送日期'
) COMMENT '邮寄信息表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
USE hive_middle;

INSERT INTO delivery_info (delivery_id, order_id, user_id, order_date, custom_date)
VALUES ('1', '1', '101', '2021-09-27', '2021-09-29'),
       ('2', '2', '101', '2021-09-28', '2021-09-28'),
       ('3', '3', '101', '2021-09-29', '2021-09-30');

USE hive_middle;

CREATE TABLE IF NOT EXISTS friendship_info (
                                               user1_id STRING COMMENT '用户1 id',
                                               user2_id STRING COMMENT '用户2 id'
) COMMENT '好有信息表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
USE hive_middle;

INSERT INTO friendship_info (user1_id, user2_id)
VALUES ('101', '1010'),
       ('101', '108'),
       ('101', '106');

USE hive_middle;

CREATE TABLE IF NOT EXISTS favor_info (
                                          user_id STRING COMMENT '用户id',
                                          sku_id STRING COMMENT '商品id',
                                          create_date STRING COMMENT '收藏日期'
) COMMENT '用户收藏表'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

USE hive_middle;

INSERT INTO favor_info (user_id, sku_id, create_date)
VALUES ('101', '3', '2021-09-23'),
       ('101', '12', '2021-09-23'),
       ('101', '6', '2021-09-25');


-- 查询累积销量排名第二的商品
SELECT
    *
FROM
    (
        SELECT
            sku_id
        FROM
            (
                SELECT
                    sku_id,
                    order_num,
                    rank() OVER (ORDER BY order_num DESC) rk
                FROM
                    (
                        SELECT
                            sku_id,
                            sum(sku_num) order_num
                        FROM order_detail
                        GROUP BY sku_id
                    )t1
            )t2 WHERE t2.rk = 2
    ) t3 RIGHT JOIN (
        SELECT 1
    ) t4 ON 1 = 1;

SELECT
    sku_id,
    sum(sku_num) order_num,
    rank() OVER (ORDER BY sum(sku_num) DESC) rk
FROM order_detail
GROUP BY sku_id;

-- 去重
SELECT
    DISTINCT user_id, create_date
FROM order_info;

SELECT
    user_id, create_date
FROM order_info
GROUP BY user_id, create_date;

SELECT
    user_id,
    create_date
FROM
    (
        SELECT
            user_id,
            create_date,
            row_number() OVER (PARTITION BY user_id, create_date) rn
        FROM order_info
    ) t1
WHERE rn = 1;

-- 至少连续三天下单的用户
SELECT
    DISTINCT user_id
FROM
    (
        SELECT
            user_id,
            datediff(lead2, create_date) diff
        FROM
            (
                SELECT
                    user_id,
                    create_date,
                    lead(create_date, 2, '9999-12-31') OVER (PARTITION BY user_id ORDER BY create_date) lead2
                FROM
                    (
                        SELECT
                            DISTINCT user_id, create_date
                        FROM order_info
                    ) t1
            ) t2
    ) t3
WHERE t3.diff = 2;

SELECT
    DISTINCT user_id
FROM
    (
        SELECT
            user_id,
            diff,
            count(*) cnt
        FROM
            (
                SELECT
                    user_id,
                    create_date,
                    rank() OVER (PARTITION BY user_id ORDER BY create_date) rk,
                    date_sub(create_date, rank() OVER (PARTITION BY user_id ORDER BY create_date)) diff
                FROM
                    (
                        SELECT
                            DISTINCT user_id, create_date
                        FROM order_info
                    ) t1
            ) t2
        GROUP BY user_id, diff
        HAVING cnt >= 3
    ) t3;


SELECT
    DISTINCT user_id
FROM
    (
        SELECT
            user_id,
            ts,
            count(*) OVER (PARTITION BY user_id ORDER BY ts
                RANGE BETWEEN 86400 PRECEDING and 86400 FOLLOWING) cnt
        FROM
            (
                SELECT
                    user_id,
                    unix_timestamp(create_date, 'yyyy-MM-dd') ts
                FROM
                    (
                        SELECT
                            DISTINCT user_id, create_date
                        FROM order_info
                    ) t1
            ) t2
    )t3
WHERE cnt = 3;

-- 各品类销售商品的种类数以及销量最高商品
SELECT
    category_id,
    sku_id,
    name,
    order_num,
    cnt
FROM
    (
        SELECT
            od.sku_id,
            name,
            order_num,
            sku_info.category_id,
            rank() OVER (PARTITION BY category_id ORDER BY order_num DESC) rk,
            count(*) OVER(PARTITION BY category_id) cnt
        FROM
            (
                SELECT
                    sku_id,
                    sum(sku_num) order_num
                FROM order_detail
                GROUP BY sku_id
            ) od
                LEFT JOIN sku_info ON od.sku_id = sku_info.sku_id
    ) t1
WHERE rk = 1;

-- 用户累计消费金额及VIP等级
SELECT
    t2.user_id,
    t2.create_date,
    t2.amount_sofar,
    CASE
        WHEN amount_sofar >= 100000 THEN '钻石会员'
        WHEN amount_sofar >= 80000 THEN '白金会员'
        WHEN amount_sofar >= 50000 THEN '黄金会员'
        WHEN amount_sofar >= 30000 THEN '白银会员'
        WHEN amount_sofar >= 10000 THEN '青铜会员'
        END vip_level
FROM
    (
        SELECT
            user_id,
            create_date,
            sum(amount_by_day) OVER (PARTITION BY user_id ORDER BY create_date) amount_sofar
        FROM
            (
                SELECT
                    user_id,
                    create_date,
                    sum(total_amount) amount_by_day
                FROM order_info
                GROUP BY user_id, create_date
            ) t1
    ) t2;

-- 首次下单后第二天连续下单的用户比率
SELECT
    sum(if(datediff(second, first) = 1, 1, 0)) / count(*)
FROM
    (
        SELECT
            user_id,
            min(create_date) first,
            max(create_date) second
        FROM
            (
                SELECT
                    user_id,
                    create_date
                FROM (
                         SELECT
                             user_id,
                             create_date,
                             rank() OVER (PARTITION BY user_id ORDER BY create_date) rk
                         FROM
                             (
                                 SELECT
                                     DISTINCT user_id, create_date
                                 FROM order_info
                             ) t1
                     ) t2
                WHERE rk <= 2
            ) t3
        GROUP BY user_id
    ) t4;

-- 向所有用户推荐其朋友收藏但是自己未收藏的商品
SELECT
    user1_id,
    sku_id
FROM
    (
        SELECT
            DISTINCT
            user1_id,
            sku_id
        FROM
            (
                SELECT
                    user1_id,
                    user2_id
                FROM friendship_info
                UNION
                SELECT
                    user2_id,
                    user1_id
                FROM friendship_info
            ) t1
                LEFT JOIN favor_info f ON t1.user2_id = f.user_id
    ) t2
        LEFT JOIN
    (
        SELECT
            user_id,
            collect_list(sku_id) skus
        FROM favor_info
        GROUP BY user_id
    ) t3 ON t2.user1_id = t3.user_id
WHERE !array_contains(skus, t2.sku_id);


SELECT
    user1_id,
    t2.sku_id
FROM
    (
        SELECT
            DISTINCT
            user1_id,
            sku_id
        FROM
            (
                SELECT
                    user1_id,
                    user2_id
                FROM friendship_info
                UNION
                SELECT
                    user2_id,
                    user1_id
                FROM friendship_info
            ) t1
                LEFT JOIN favor_info f ON t1.user2_id = f.user_id
    ) t2
        LEFT JOIN
    (
        SELECT
            user_id,
            sku_id
        FROM favor_info
    ) t3 ON t2.user1_id = t3.user_id AND t2.sku_id = t3.sku_id
WHERE t3.user_id IS NULL;

-- 同时在线最多的人数
SELECT
    max(s)
FROM
    (
        SELECT
            event_ts,
            sum(cnt) OVER (ORDER BY event_ts ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) s
        FROM
            (
                SELECT
                    login_ts event_ts,
                    1 cnt
                FROM user_login_detail
                UNION ALL
                SELECT
                    logout_ts event_ts,
                    -1 cnt
                FROM user_login_detail
            ) t1
    ) t2;

-- UDF
ADD JAR /opt/module/datas/hive-1.0-SNAPSHOT.jar;

CREATE TEMPORARY FUNCTION my_len AS 'com.kim.hive.udf.MyLength';

SHOW FUNCTIONS LIKE '*my_len*';

SELECT my_len('aaasssfddf');

CREATE FUNCTION my_len2 AS 'com.kim.hive.udf.MyLength' USING JAR "hdfs://hadoop104:8020/udf/hive-1.0-SNAPSHOT.jar";

SELECT my_len2('aaasssfddf');

-- 分区表
CREATE DATABASE partition_bucket;
USE partition_bucket;
CREATE TABLE dept_partition (
                                deptno INT COMMENT '部门编号',
                                dname STRING COMMENT '部门名称',
                                loc STRING COMMENT '部门位置'
) PARTITIONED BY (day STRING)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/dept_2024.txt'
    INTO TABLE dept_partition
    PARTITION(day='20240726');

SELECT * FROM dept_partition;

INSERT OVERWRITE TABLE dept_partition PARTITION(day='20240727')
SELECT
    deptno,
    dname,
    loc
FROM dept_partition
WHERE day = '20240726';

SHOW PARTITIONS dept_partition;

ALTER TABLE dept_partition ADD PARTITION(day='20240725');

SELECT * FROM dept_partition WHERE day = '20240725';

ALTER TABLE dept_partition ADD PARTITION(day='20240724') PARTITION(day='20240723');

ALTER TABLE dept_partition DROP PARTITION(day='20240723');

ALTER TABLE dept_partition DROP PARTITION(day='20240725'), PARTITION(day='20240724');

-- 手动创建hdfs分区，修复分区
ALTER TABLE dept_partition ADD PARTITION (day='20240725');

-- 手动删除hdfs分区，修复分区
ALTER TABLE dept_partition DROP PARTITION(day='20240725');

MSCK REPAIR TABLE dept_partition ADD PARTITIONS;
MSCK REPAIR TABLE dept_partition DROP PARTITIONS;
MSCK REPAIR TABLE dept_partition SYNC PARTITIONS;

USE partition_bucket;
CREATE TABLE dept_partition2 (
                                 deptno INT COMMENT '部门编号',
                                 dname STRING COMMENT '部门名称',
                                 loc STRING COMMENT '部门位置'
) PARTITIONED BY (day STRING, hour STRING)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/dept_2024.txt'
    INTO TABLE dept_partition2
    PARTITION (day='20240726', hour='09');

SELECT * FROM dept_partition2;

SET hive.exec.dynamic.paritition=true;

SET hive.exec.dynamic.partition.mode=nonstrict;

SET hive.exec.max.dynamic.partitions=1000;

SET hive.exec.max.dynamic.partitions.pernode=100;

SET hive.exec.max.created.files=100000;

SET hive.error.on.empty.partition=false;

USE partition_bucket;
CREATE TABLE dept_partition_dynamic (
                                        id INT,
                                        name STRING
) PARTITIONED BY (loc INT)
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';


INSERT OVERWRITE TABLE partition_bucket.dept_partition_dynamic PARTITION (loc)
SELECT
    deptno,
    dname,
    loc
FROM default.dept;

SHOW PARTITIONS partition_bucket.dept_partition_dynamic;

--分桶表
USE partition_bucket;
CREATE TABLE stu_bucket (
                            id INT,
                            name STRING
) CLUSTERED BY (id) INTO 4 BUCKETS
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/student.txt' INTO TABLE stu_bucket;

CREATE TABLE stu_bucket_sort (
                                 id INT,
                                 name STRING
) CLUSTERED BY (id) SORTED BY (id) INTO 4 BUCKETS
    ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';

LOAD DATA LOCAL INPATH '/opt/module/datas/student.txt' INTO TABLE stu_bucket_sort;

-- 文件格式
CREATE DATABASE file_format;
USE file_format;
CREATE TABLE test_orc (
                          id INT,
                          name STRING
) STORED AS ORC;

SHOW CREATE TABLE test_orc;

-- 文本文件声明使用压缩
SET hive.exec.compress.output=true;
SET mapreduce.output.fileoutputformat.compress.codec=org.apache.hadoop.io.compress.SnappyCodec;

-- 计算过程压缩
-- 单个mr中间结果压缩
SET mapreduce.map.output.compress=true;
SET mapreduce.map.output.compress.codec=org.apache.hadoop.io.compress.SnappyCodec;

-- 单条SQL语句中间结果进行压缩
SET hive.exec.compress.intermediate=true;
SET hive.intermediate.compression.codec=org.apahce.hadoop.io.compress.SnappyCodec;

-- yarn资源配置:yarn-site.xml
-- nodemanager分配给container的内存，cpu
-- yarn.nodemanager.resource.memory-mb
-- yarn.nodemanager.resource.cpu-vcores
--
-- yarn.scheduler.maximum-allocation-mb
-- yarn.scheduler.minmum-allocation-mb

-- mapreduce资源配置
-- maptask
-- mapreduce.map.memory.mb
-- mapreduce.map.cpu.vcores
-- reducetask
-- mapreduce.reduce.memory.mb
-- mapreduce.reduce.cpu.vcores

-- explain
USE hive_middle;
EXPLAIN SELECT
            order_id,
            count(*)
        FROM order_detail
        GROUP BY order_id;

--分组聚合优化
SET hive.map.aggr=true;
SET hive.map.aggr.hash.min.reduction=0.5;
SET hive.groupby.mapaggr.checkinterval=100000;
SET hive.map.aggr.hash.force.flush.memory.threshold=0.9;

-- join优化
-- map-join
SET hive.auto.convert.join=true;
SET hive.mapjoin.smalltable.filesize=250000;
SET hive.auto.convert.join.noconditionaltask=true;
SET hive.auto.convert.join.noconditionaltask.size=1000000;

-- bucket map join
SET hive.cbo.enable=false;
SET hive.ignore.mapjoin.hint=false;
SET hive.optimize.bucketmapjoin=true;

-- sort merge bucket join
SET hive.optimize.bucketmapjoin.sortedmerge=true;
SET hive.auto.convert.sortmerge.join=true;

-- 数据倾斜
-- 分组数据倾斜
SET hive.map.aggr=true;
SET hive.map.aggr.hash.min.reduction=0.5;
SET hive.groupby.mapaggr.checkinterval=100000;
SET hive.map.aggr.hash.force.flush.memory.threshold=0.9;

-- skew-groupby
SET hive.groupby.skewindata=true;

-- join数据倾斜(大表对大表)
-- MAPJOIN

-- skew-join
SET hive.optimize.skewjoin=true;
SET hive.skewjoin.key=100000;

SELECT
    *
FROM
    (
        SELECT -- 打散
               concat(id, '_', cast(rand() * 2 AS INT)) id,
               value
        FROM a
    ) ta
        JOIN
    (
        SELECT -- 扩容
               concat(id, '_', 0) id,
               value
        FROM b
        UNION ALL
        SELECT
            concat(id, '_', 1) id,
            value
        FROM b
    ) tb
    ON ta.id = tb.id;

-- 任务并行度优化
-- map端
SET hive.input.format=org.apapche.hadoop.ql.io.CombineHiveInputFormat;
-- map复杂查询逻辑
SET mapreduce.input.fileinputformat.split.maxsize=25600000;

-- reduce端
SET mapreduce.job.reduces;
SET hive.exec.reducers.max;
SET hive.exec.reducers.byte.per.reducer;

-- 小文件合并优化
-- map端
SET hive.input.input.format=org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;

-- reduce端
SET hive.merge.mapfiles=true;

SET hive.merge.mapredfiles=true;

SET hvie.merge.size.per.task=256000000;

SET hive.merge.smallfiles.avgsize=16000000;


-- CBO优化
SET hive.cbo.enable=true;
-- 谓词下推优化
SET hive.optimize.ppd=true;
-- 矢量计算
SET hive.vectorized.execution.enable=true;
-- fetch抓取
SET hive.fetch.task.convertion=more;
-- 本地模式
SET hive.exec.mode.local.auto=true;
SET hive.exec.mode.local.auto.inputbytes.max=50000000;
SET hive.exec.mode.local.auto.input.files.max=10;
-- 并行执行(多个mr之间的并行)
SET hive.exec.parallel=true;
SET hive.exec.parallel.thread.number=8;
-- 严格模式
SET hive.strict.checks.no.partition.filter=true;
SET hive.strict.checks.orderby.no.limit=true;
SET hive.strict.checks.cartesian.product=true;



