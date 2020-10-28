// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 2.0
 */
public class CollectionHelper {

    private static final Log log = LogFactory.getLog(CollectionHelper.class);

    public static int size(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static <E> List<E> getNonNullList(List<E> list) {
        return (list == null) ? Collections.<E>emptyList() : list;
    }

    public static <E> List<E> filter(Collection<? extends E> list, Predicate<E> predicate) {
        return Lists.newArrayList(Iterators.filter(list.iterator(), predicate));
    }

    public static <E, T> List<E> transform(List<? extends T> list, Function<T, E> function) {
        return Lists.newArrayList(Lists.transform(list, function));
    }

    public static <E, T> List<E> transformToUniqueList(List<? extends T> list, Function<T, E> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Sets.newHashSet(transform(list, function)));
    }

    public static <E, T, L> Map<E, L> newHashMap(List<? extends T> list, Function<T, E> keyFunction,
                                                 Function<T, L> valueFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_MAP;
        }
        Map<E, L> map = Maps.newHashMapWithExpectedSize(list.size());
        for (T t : list) {
            map.put(keyFunction.apply(t), valueFunction.apply(t));
        }
        return map;
    }

    public static <E, T> Map<E, T> newHashMap(List<? extends T> list, Function<T, E> function) {
        Map<E, T> map = Maps.newHashMap();
        for (T t : list) {
            map.put(function.apply(t), t);
        }
        return map;
    }

    public static<K, V, T> List<T> flatMap(Map<K, V> map, Function<Map.Entry<K, V>, T> function) {
        List<T> list = Lists.newArrayList();
        for (Map.Entry entry : map.entrySet()) {
            list.add((T) function.apply(entry));
        }
        return list;
    }

    public static <E> List<E> union(List<E> first, List<E> second) {
        List<E> result = Lists.newArrayList(first);
        result.addAll(second);
        return result;
    }

    public static <E> Set<E> union(Set<E> first, Set<E> second) {
        HashSet<E> result = Sets.newHashSet(first);
        result.addAll(second);
        return result;
    }

    public static <E> Set<E> intersect(Set<E> first, Set<E> second) {
        Preconditions.checkNotNull(first);
        Preconditions.checkNotNull(second);

        // Use the smaller set as first argument.
        if (first.size() > second.size()) {
            return Sets.intersection(second, first);
        } else {
            return Sets.intersection(first, second);
        }
    }

    /**
     * Shuffle a list without changing the original list.
     */
    public static <E> List<E> shuffle(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        // If list has only one element, just return it.
        if (list.size() == 1) {
            return list;
        }
        List<E> result = Lists.newArrayList(list);
        Collections.shuffle(result);
        return result;
    }

    /**
     * Process list in batch.
     *
     * @param list The list.
     * @param batchSize The batch size.
     * @param processor The processor implementation.
     * @return The batch count.
     */
    public static <E> int processInBatches(List<E> list, int batchSize,
                                           BatchProcessor<E> processor) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        int batchCount = 0;
        for (int from = 0; from < list.size(); from += batchSize) {
            int to = Math.min(from + batchSize, list.size());
            // Construct a new list, because subList will affect the original list.
            processor.process(Lists.newArrayList(list.subList(from, to)));
            batchCount++;
        }
        return batchCount;
    }

    public static <E> void processAndClearIfReachBatchSize(List<E> list, int batchSize,
                                                           BatchProcessor<E> processor) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (list.size() >= batchSize) {
            log.info(new LogMessageBuilder()
                    .withMessage("Reach the batch size.")
                    .withParameter("listSize", list.size()));
            processor.process(Lists.newArrayList(list));
            list.clear();
        } else {
            log.info(new LogMessageBuilder()
                    .withMessage("Not reach the batch size.")
                    .withParameter("listSize", list.size()));
        }
    }

    public static <E, R, ERR extends Exception> List<R> callInBatches(List<E> list, int batchSize,
                                                                      BatchCaller<E, R, ERR> caller) throws ERR {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<R> result = Lists.newArrayList();
        for (int from = 0; from < list.size(); from += batchSize) {
            int to = Math.min(from + batchSize, list.size());
            // Construct a new list, because subList will affect the original list.
            List<R> batchResult = caller.call(Lists.newArrayList(list.subList(from, to)));
            if (CollectionUtils.isNotEmpty(batchResult)) {
                result.addAll(batchResult);
            }
        }
        return result;
    }

    public static <E, R, ERR extends Exception> List<R> callInBatches(List<E> list, int batchSize,
            BatchCallerWithIndex<E, R, ERR> caller) throws ERR {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<R> result = Lists.newArrayList();
        for (int from = 0; from < list.size(); from += batchSize) {
            int to = Math.min(from + batchSize, list.size());
            // Construct a new list, because subList will affect the original list.
            List<R> batchResult = caller.call(Lists.newArrayList(list.subList(from, to)), from, to);
            if (CollectionUtils.isNotEmpty(batchResult)) {
                result.addAll(batchResult);
            }
        }
        return result;
    }

    public static <E, R, ERR extends Exception> List<R> callInBatchesInParallel(List<E> list, int batchSize,
            FutureBatchCaller<E, R, ERR> caller) throws ERR, ExecutionException, InterruptedException {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<R> result = Lists.newArrayList();
        List<Future<List<R>>> futures = Lists.newArrayList();
        for (int from = 0; from < list.size(); from += batchSize) {
            int to = Math.min(from + batchSize, list.size());
            // Construct a new list, because subList will affect the original list.
            futures.add(caller.call(Lists.newArrayList(list.subList(from, to))));
        }

        for (Future<List<R>> future : futures) {
            result.addAll(future.get());
        }

        return result;
    }

    public static <E, R, ERR extends Exception> List<R> callInParallel(List<E> list,
            final FutureCaller<E, R, ERR> caller) throws ERR, ExecutionException, InterruptedException {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<R> result = Lists.newArrayListWithExpectedSize(list.size());
        List<Future<R>> futures = Lists.newArrayList();
        for (E object : list) {
            // Construct a new list, because subList will affect the original list.
            futures.add(caller.call(object));
        }

        for (Future<R> future : futures) {
            result.add(future.get());
        }

        return result;
    }

    public static <E, R, ERR extends Exception> void callInParallelQuietly(List<E> list,
            final FutureCaller<E, R, ERR> caller) throws ERR {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<Future<R>> futures = Lists.newArrayList();
        for (E object : list) {
            // Construct a new list, because subList will affect the original list.
            futures.add(caller.call(object));
        }

        for (Future<R> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                // Ignore.
            }
        }

    }

    public static <E> void consumeInBatches(Iterable<E> iterable, int batchSize,
                                            BatchProcessor<E> processor) {
        if (iterable == null) {
            return;
        }

        List<E> batch = Lists.newArrayList();
        for (E item : iterable) {
            batch.add(item);
            if (batch.size() >= batchSize) {
                processor.process(batch);
                batch = Lists.newArrayList();
            }
        }
        if (!batch.isEmpty()) {
            processor.process(batch);
        }
    }

    public static <E> List<E> sort(List<E> list, final String fieldName, final boolean reverse) {
        List<E> sortedList = Lists.newArrayList(list);
        Collections.sort(sortedList, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                try {
                    Comparable f1 = (Comparable) FieldUtils.readField(o1, fieldName, true);
                    Comparable f2 = (Comparable) FieldUtils.readField(o2, fieldName, true);
                    return reverse ? -f1.compareTo(f2) : f1.compareTo(f2);
                } catch (IllegalAccessException e) {
                    log.error(new LogMessageBuilder()
                            .withMessage("Error occured while compare.")
                            .withParameter("o1", o1)
                            .withParameter("o2", o2)
                            .withParameter("fieldName", fieldName)
                            .build());
                    return 0;
                }
            }
        });
        return sortedList;
    }

    public static <E, T> List<E> mergeList(List<List<T>> listList, Function<List<T>, E> function) {
        if (CollectionUtils.isEmpty(listList)) {
            return Collections.emptyList();
        }

        List<T> firstList = listList.get(0);
        for (int i = 1; i < listList.size(); ++i) {
            Preconditions.checkNotNull(listList.get(i));
            Preconditions.checkArgument(listList.get(i).size() == firstList.size());
        }

        List<E> result = Lists.newArrayList();
        for (int i = 0; i < firstList.size(); ++i) {
            final int index = i;
            result.add(function.apply(transform(listList, new Function<List<T>, T>() {
                @Override
                public T apply(List<T> input) {
                    return input.get(index);
                }
            })));
        }

        return result;
    }

    public static int sumInteger(Iterable<Integer> list) {
        Integer sum = 0;
        for (Integer item : list) {
            sum += item;
        }
        return sum;
    }

    public static long sumLong(Iterable<Long> list) {
        long sum = 0L;
        for (Long item : list) {
            sum += item;
        }
        return sum;
    }

    public static float sumFloat(Iterable<Float> list) {
        Float sum = 0f;
        for (Float item : list) {
            sum += item;
        }
        return sum;
    }

    public static double sumDouble(Iterable<Double> list) {
        double sum = 0;
        for (Double item : list) {
            sum += item;
        }
        return sum;
    }

    /*
     * Note that here we define the empty list same as null list.
     */
    public static <T> boolean isSameListIgnoreNullList(List<T> list1, List<T> list2) {
        return (CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2))
                || Objects.equal(list1, list2);
    }

    public static <T> T callInRetryMode(VerifiableCaller<T> caller,
                                        int maxRetryTimes) {
        int retryTime = 0;
        for (; retryTime < maxRetryTimes - 1; ++retryTime) {
            try {
                log.info(new LogMessageBuilder("Call in retry mode.")
                        .withParameter("retryTime", retryTime));
                T result = caller.call();
                if (caller.isRetriable(result)) {
                    continue;
                } else {
                    return result;
                }
            } catch (Throwable t) {
                log.warn(new LogMessageBuilder("Error while call in retry mode.")
                        .withParameter("retryTime", retryTime), t);
                continue;
            }
        }
        log.info(new LogMessageBuilder("Call in retry mode.")
                .withParameter("retryTime", retryTime));
        return caller.call();
    }

    public static <T> T callInRetryModeWithPowDelay(VerifiableCaller<T> caller,
                                                    int maxRetryTimes, long milliseconds) {
        int retryTime = 0;
        for (; retryTime < maxRetryTimes - 1; ++retryTime) {
            boolean retry = false;
            try {
                log.info(new LogMessageBuilder("Call in retry mode.")
                        .withParameter("retryTime", retryTime));
                T result = caller.call();
                if (caller.isRetriable(result)) {
                    // Wait and retry.
                    ThreadUtils.sleep((long) (milliseconds * Math.pow(2, retryTime + 1)));
                    continue;
                } else {
                    return result;
                }
            } catch (Throwable t) {
                log.warn(new LogMessageBuilder("Error while call in retry mode.")
                        .withParameter("retryTime", retryTime), t);
                // Wait and retry.
                ThreadUtils.sleep((long) (milliseconds * Math.pow(2, retryTime + 1)));
                continue;
            }
        }
        log.info(new LogMessageBuilder("Call in retry mode.")
                .withParameter("retryTime", retryTime));
        return caller.call();
    }

    public interface BatchProcessor<E> {

        void process(List<E> list);

    }

    public interface BatchCaller<E, R, Err extends Throwable> {

        List<R> call(List<E> list) throws Err;

    }

    public interface BatchCallerWithIndex<E, R, Err extends Throwable> {

        List<R> call(List<E> list, int start, int to) throws Err;

    }

    public interface FutureBatchCaller<E, R, Err extends Throwable> {

        Future<List<R>> call(List<E> list) throws Err;

    }

    public interface FutureCaller<E, R, Err extends Throwable> {

        Future<R> call(E object) throws Err;

    }

    public interface VerifiableCaller<T> {

        T call();

        boolean isRetriable(T r);

    }
}