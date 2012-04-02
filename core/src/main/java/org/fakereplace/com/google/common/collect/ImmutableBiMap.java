/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fakereplace.com.google.common.collect;

import java.util.Map;

import org.fakereplace.com.google.common.annotations.GwtCompatible;


/**
 * An immutable {@link BiMap} with reliable user-specified iteration order. Does
 * not permit null keys or values. An {@code ImmutableBiMap} and its inverse
 * have the same iteration ordering.
 * <p/>
 * <p>An instance of {@code ImmutableBiMap} contains its own data and will
 * <i>never</i> change. {@code ImmutableBiMap} is convenient for
 * {@code public static final} maps ("constant maps") and also lets you easily
 * make a "defensive copy" of a bimap provided to your class by a caller.
 * <p/>
 * <p><b>Note</b>: Although this class is not final, it cannot be subclassed as
 * it has no public or protected constructors. Thus, instances of this class are
 * guaranteed to be immutable.
 *
 * @author Jared Levy
 */
@GwtCompatible(serializable = true)
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V>
        implements BiMap<K, V> {

    private static final ImmutableBiMap<Object, Object> EMPTY_IMMUTABLE_BIMAP
            = new EmptyBiMap();

    /**
     * Returns the empty bimap.
     */
    // Casting to any type is safe because the set will never hold any elements.
    @SuppressWarnings("unchecked")
    public static <K, V> ImmutableBiMap<K, V> of() {
        return (ImmutableBiMap<K, V>) EMPTY_IMMUTABLE_BIMAP;
    }

    /**
     * Returns an immutable bimap containing a single entry.
     */
    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1));
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys or values are added
     */
    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2));
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys or values are added
     */
    public static <K, V> ImmutableBiMap<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(
                k1, v1, k2, v2, k3, v3));
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys or values are added
     */
    public static <K, V> ImmutableBiMap<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(
                k1, v1, k2, v2, k3, v3, k4, v4));
    }

    /**
     * Returns an immutable map containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys or values are added
     */
    public static <K, V> ImmutableBiMap<K, V> of(
            K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(
                k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }

    // looking for of() with > 5 entries? Use the builder instead.

    /**
     * Returns a new builder. The generated builder is equivalent to the builder
     * created by the {@link Builder} constructor.
     */
    public static <K, V> Builder<K, V> builder() {
        return new Builder<K, V>();
    }

    /**
     * A builder for creating immutable bimap instances, especially {@code public
     * static final} bimaps ("constant bimaps"). Example: <pre>   {@code
     * <p/>
     *   static final ImmutableBiMap<String, Integer> WORD_TO_INT =
     *       new ImmutableBiMap.Builder<String, Integer>()
     *           .put("one", 1)
     *           .put("two", 2)
     *           .put("three", 3)
     *           .build();}</pre>
     * <p/>
     * For <i>small</i> immutable bimaps, the {@code ImmutableBiMap.of()} methods
     * are even more convenient.
     * <p/>
     * <p>Builder instances can be reused - it is safe to call {@link #build}
     * multiple times to build multiple bimaps in series. Each bimap is a superset
     * of the bimaps created before it.
     */
    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {

        /**
         * Creates a new builder. The returned builder is equivalent to the builder
         * generated by {@link ImmutableBiMap#builder}.
         */
        public Builder() {
        }

        /**
         * Associates {@code key} with {@code value} in the built bimap. Duplicate
         * keys or values are not allowed, and will cause {@link #build} to fail.
         */
        @Override
        public Builder<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }

        /**
         * Associates all of the given map's keys and values in the built bimap.
         * Duplicate keys or values are not allowed, and will cause {@link #build}
         * to fail.
         *
         * @throws NullPointerException if any key or value in {@code map} is null
         */
        @Override
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        /**
         * Returns a newly-created immutable bimap.
         *
         * @throws IllegalArgumentException if duplicate keys or values were added
         */
        @Override
        public ImmutableBiMap<K, V> build() {
            ImmutableMap<K, V> map = super.build();
            if (map.isEmpty()) {
                return of();
            }
            return new RegularImmutableBiMap<K, V>(super.build());
        }
    }

    /**
     * Returns an immutable bimap containing the same entries as {@code map}. If
     * {@code map} somehow contains entries with duplicate keys (for example, if
     * it is a {@code SortedMap} whose comparator is not <i>consistent with
     * equals</i>), the results of this method are undefined.
     * <p/>
     * <p><b>Note:</b> If {@code map} is an {@code ImmutableBiMap}, the given map
     * itself will be returned.
     *
     * @throws IllegalArgumentException if two keys have the same value
     * @throws NullPointerException     if any key or value in {@code map} is null
     */
    public static <K, V> ImmutableBiMap<K, V> copyOf(
            Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            @SuppressWarnings("unchecked") // safe since map is not writable
                    ImmutableBiMap<K, V> bimap = (ImmutableBiMap<K, V>) map;
            return bimap;
        }

        if (map.isEmpty()) {
            return of();
        }

        ImmutableMap<K, V> immutableMap = ImmutableMap.copyOf(map);
        return new RegularImmutableBiMap<K, V>(immutableMap);
    }

    ImmutableBiMap() {
    }

    abstract ImmutableMap<K, V> delegate();

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The inverse of an {@code ImmutableBiMap} is another
     * {@code ImmutableBiMap}.
     */
    public abstract ImmutableBiMap<V, K> inverse();

    @Override
    public boolean containsKey(Object key) {
        return delegate().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inverse().containsKey(value);
    }

    @Override
    public ImmutableSet<Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    @Override
    public V get(Object key) {
        return delegate().get(key);
    }

    @Override
    public ImmutableSet<K> keySet() {
        return delegate().keySet();
    }

    /**
     * Returns an immutable set of the values in this map. The values are in the
     * same order as the parameters used to build this map.
     */
    @Override
    public ImmutableSet<V> values() {
        return inverse().keySet();
    }

    /**
     * Guaranteed to throw an exception and leave the bimap unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    public V forcePut(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public int size() {
        return delegate().size();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override
    public String toString() {
        return delegate().toString();
    }

    /**
     * Bimap with no mappings.
     */
    @SuppressWarnings("serial") // uses writeReplace(), not default serialization
    static class EmptyBiMap extends ImmutableBiMap<Object, Object> {
        @Override
        ImmutableMap<Object, Object> delegate() {
            return ImmutableMap.of();
        }

        @Override
        public ImmutableBiMap<Object, Object> inverse() {
            return this;
        }

        Object readResolve() {
            return EMPTY_IMMUTABLE_BIMAP; // preserve singleton property
        }
    }

    /**
     * Serialized type for all ImmutableBiMap instances. It captures the logical
     * contents and they are reconstructed using public factory methods. This
     * ensures that the implementation types remain as implementation details.
     * <p/>
     * Since the bimap is immutable, ImmutableBiMap doesn't require special logic
     * for keeping the bimap and its inverse in sync during serialization, the way
     * AbstractBiMap does.
     */
    private static class SerializedForm extends ImmutableMap.SerializedForm {
        SerializedForm(ImmutableBiMap<?, ?> bimap) {
            super(bimap);
        }

        @Override
        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>();
            return createMap(builder);
        }

        private static final long serialVersionUID = 0;
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }
}
