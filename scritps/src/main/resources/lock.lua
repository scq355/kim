
-- 解锁操作
if redis.call('get', KEYS[1]) == ARGV[1] then
    return redis.call('del', KEYS[1])
else
    return 0
end

-- 可重入锁加锁
-- if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then redis.call('hincrby', KEYS[1], ARGV[1], 1) redis.call('expire', KEYS[1], ARGV[2]) return 1 else return 0 end
if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
    redis.call('hincrby', KEYS[1], ARGV[1], 1)
    redis.call('expire', KEYS[1], ARGV[2])
    return 1
else
    return 0
end

-- 可重入锁解锁
-- if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then return nil elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then return redis.call('del', KEYS[1]) else return 0 end
if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then
    return nil
elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then
    return redis.call('del', KEYS[1])
else
    return 0
end

-- 自动续期
-- if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end
if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then
    return redis.call('expire', KEYS[1], ARGV[2])
else
    return 0
end


