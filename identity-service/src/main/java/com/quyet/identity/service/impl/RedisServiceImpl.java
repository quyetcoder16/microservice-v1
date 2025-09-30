package com.quyet.identity.service.impl;

import com.quyet.identity.service.RedisService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisServiceImpl implements RedisService {

  RedisTemplate<String, Object> redisTemplate;

  @Override
  public void setValue(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
    } catch (RedisSystemException e) {
      log.error("Error setting value for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to set value in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error setting value for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public void setValueWithTTL(String key, Object value, long timeout, TimeUnit unit) {
    try {
      redisTemplate.opsForValue().set(key, value, timeout, unit);
    } catch (RedisSystemException e) {
      log.error("Error setting value with TTL for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to set value with TTL in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error setting value with TTL for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public Object getValue(String key) {
    try {
      return redisTemplate.opsForValue().get(key);
    } catch (RedisSystemException e) {
      log.error("Error getting value for key {}: {}", key, e.getMessage(), e);
      return null;
    } catch (Exception e) {
      log.error("Unexpected error getting value for key {}: {}", key, e.getMessage(), e);
      return null;
    }
  }

  @Override
  public void deleteKey(String key) {
    try {
      redisTemplate.delete(key);
    } catch (RedisSystemException e) {
      log.error("Error deleting key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to delete key in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error deleting key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public boolean hasKey(String key) {
    try {
      return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    } catch (RedisSystemException e) {
      log.error("Error checking key existence {}: {}", key, e.getMessage(), e);
      return false;
    } catch (Exception e) {
      log.error("Unexpected error checking key existence {}: {}", key, e.getMessage(), e);
      return false;
    }
  }

  // Hash operations

  @Override
  public void setHash(String key, String field, Object value) {
    try {
      redisTemplate.opsForHash().put(key, field, value);
    } catch (RedisSystemException e) {
      log.error("Error setting hash field {} for key {}: {}", field, key, e.getMessage(), e);
      throw new RuntimeException("Failed to set hash field in Redis", e);
    } catch (Exception e) {
      log.error(
          "Unexpected error setting hash field {} for key {}: {}", field, key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public void setHashWithTTL(String key, String field, Object value, long timeout, TimeUnit unit) {
    try {
      redisTemplate.opsForHash().put(key, field, value);
      redisTemplate.expire(key, timeout, unit);
    } catch (RedisSystemException e) {
      log.error(
          "Error setting hash field {} with TTL for key {}: {}", field, key, e.getMessage(), e);
      throw new RuntimeException("Failed to set hash field with TTL in Redis", e);
    } catch (Exception e) {
      log.error(
          "Unexpected error setting hash field {} with TTL for key {}: {}",
          field,
          key,
          e.getMessage(),
          e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public Object getHash(String key, String field) {
    try {
      return redisTemplate.opsForHash().get(key, field);
    } catch (RedisSystemException e) {
      log.error("Error getting hash field {} for key {}: {}", field, key, e.getMessage(), e);
      return null;
    } catch (Exception e) {
      log.error(
          "Unexpected error getting hash field {} for key {}: {}", field, key, e.getMessage(), e);
      return null;
    }
  }

  @Override
  public Map<Object, Object> getAllHash(String key) {
    try {
      return redisTemplate.opsForHash().entries(key);
    } catch (RedisSystemException e) {
      log.error("Error getting all hash entries for key {}: {}", key, e.getMessage(), e);
      return Collections.emptyMap();
    } catch (Exception e) {
      log.error("Unexpected error getting all hash entries for key {}: {}", key, e.getMessage(), e);
      return Collections.emptyMap();
    }
  }

  @Override
  public void deleteHashField(String key, String... fields) {
    try {
      redisTemplate.opsForHash().delete(key, (Object[]) fields);
    } catch (RedisSystemException e) {
      log.error("Error deleting hash fields for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to delete hash fields in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error deleting hash fields for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public boolean hasHashField(String key, String field) {
    try {
      return redisTemplate.opsForHash().hasKey(key, field);
    } catch (RedisSystemException e) {
      log.error("Error checking hash field {} for key {}: {}", field, key, e.getMessage(), e);
      return false;
    } catch (Exception e) {
      log.error(
          "Unexpected error checking hash field {} for key {}: {}", field, key, e.getMessage(), e);
      return false;
    }
  }

  // List operations
  @Override
  public void addToList(String key, Object... values) {
    try {
      redisTemplate.opsForList().rightPushAll(key, values);
    } catch (RedisSystemException e) {
      log.error("Error adding to list for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to add to list in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error adding to list for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public List<Object> getList(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (RedisSystemException e) {
      log.error("Error getting list for key {}: {}", key, e.getMessage(), e);
      return Collections.emptyList();
    } catch (Exception e) {
      log.error("Unexpected error getting list for key {}: {}", key, e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @Override
  public long getListSize(String key) {
    try {
      Long size = redisTemplate.opsForList().size(key);
      return size != null ? size : 0;
    } catch (RedisSystemException e) {
      log.error("Error getting list size for key {}: {}", key, e.getMessage(), e);
      return 0;
    } catch (Exception e) {
      log.error("Unexpected error getting list size for key {}: {}", key, e.getMessage(), e);
      return 0;
    }
  }

  @Override
  public void removeFromList(String key, long count, Object value) {
    try {
      redisTemplate.opsForList().remove(key, count, value);
    } catch (RedisSystemException e) {
      log.error("Error removing from list for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to remove from list in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error removing from list for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public void setListWithTTL(String key, List<Object> values, long timeout, TimeUnit unit) {
    try {
      redisTemplate.opsForList().rightPushAll(key, values);
      redisTemplate.expire(key, timeout, unit);
    } catch (RedisSystemException e) {
      log.error("Error setting list with TTL for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to set list with TTL in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error setting list with TTL for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  // Set operations
  @Override
  public void addToSet(String key, Object... values) {
    try {
      redisTemplate.opsForSet().add(key, values);
    } catch (RedisSystemException e) {
      log.error("Error adding to set for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to add to set in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error adding to set for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public Set<Object> getSet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (RedisSystemException e) {
      log.error("Error getting set for key {}: {}", key, e.getMessage(), e);
      return Collections.emptySet();
    } catch (Exception e) {
      log.error("Unexpected error getting set for key {}: {}", key, e.getMessage(), e);
      return Collections.emptySet();
    }
  }

  @Override
  public boolean isMemberOfSet(String key, Object value) {
    try {
      return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    } catch (RedisSystemException e) {
      log.error("Error checking set membership for key {}: {}", key, e.getMessage(), e);
      return false;
    } catch (Exception e) {
      log.error("Unexpected error checking set membership for key {}: {}", key, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public void removeFromSet(String key, Object... values) {
    try {
      redisTemplate.opsForSet().remove(key, values);
    } catch (RedisSystemException e) {
      log.error("Error removing from set for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Failed to remove from set in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error removing from set for key {}: {}", key, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public long getSetSize(String key) {
    try {
      Long size = redisTemplate.opsForSet().size(key);
      return size != null ? size : 0;
    } catch (RedisSystemException e) {
      log.error("Error getting set size for key {}: {}", key, e.getMessage(), e);
      return 0;
    } catch (Exception e) {
      log.error("Unexpected error getting set size for key {}: {}", key, e.getMessage(), e);
      return 0;
    }
  }

  // TTL operations
  @Override
  public boolean setTTL(String key, long timeout, TimeUnit unit) {
    try {
      return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    } catch (RedisSystemException e) {
      log.error("Error setting TTL for key {}: {}", key, e.getMessage(), e);
      return false;
    } catch (Exception e) {
      log.error("Unexpected error setting TTL for key {}: {}", key, e.getMessage(), e);
      return false;
    }
  }

  @Override
  public long getTTL(String key, TimeUnit unit) {
    try {
      Long ttl = redisTemplate.getExpire(key, unit);
      return ttl != null ? ttl : -1;
    } catch (RedisSystemException e) {
      log.error("Error getting TTL for key {}: {}", key, e.getMessage(), e);
      return -1;
    } catch (Exception e) {
      log.error("Unexpected error getting TTL for key {}: {}", key, e.getMessage(), e);
      return -1;
    }
  }

  // Key management
  @Override
  public void deleteKeysByPattern(String pattern) {
    try {
      Set<String> keys = redisTemplate.keys(pattern);
      if (keys != null && !keys.isEmpty()) {
        redisTemplate.delete(keys);
      }
    } catch (RedisSystemException e) {
      log.error("Error deleting keys by pattern {}: {}", pattern, e.getMessage(), e);
      throw new RuntimeException("Failed to delete keys by pattern in Redis", e);
    } catch (Exception e) {
      log.error("Unexpected error deleting keys by pattern {}: {}", pattern, e.getMessage(), e);
      throw new RuntimeException("Unexpected error in Redis operation", e);
    }
  }

  @Override
  public Set<String> getKeysByPattern(String pattern) {
    try {
      Set<String> keys = redisTemplate.keys(pattern);
      return keys != null ? keys : Collections.emptySet();
    } catch (RedisSystemException e) {
      log.error("Error getting keys by pattern {}: {}", pattern, e.getMessage(), e);
      return Collections.emptySet();
    } catch (Exception e) {
      log.error("Unexpected error getting keys by pattern {}: {}", pattern, e.getMessage(), e);
      return Collections.emptySet();
    }
  }
}
