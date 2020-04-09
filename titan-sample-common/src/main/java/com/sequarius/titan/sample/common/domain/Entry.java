package com.sequarius.titan.sample.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * key value entry
 *
 * @author Sequarius *
 * @since 03/03/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry<K,V> {
    private K key;
    private V value;
}
