package com.shaunz.structure.graph.edge;

import com.shaunz.structure.graph.vertex.Vertex;

/**
 *  _____   _   _       ___   _   _   __   _   ______  _          __  _____   _____    _   _
 * /  ___/ | | | |     /   | | | | | |  \ | | |___  / | |        / / /  _  \ |  _  \  | | / /
 * | |___  | |_| |    / /| | | | | | |   \| |    / /  | |  __   / /  | | | | | |_| |  | |/ /
 * \___  \ |  _  |   / / | | | | | | | |\   |   / /   | | /  | / /   | | | | |  _  /  | |\ \
 *  ___| | | | | |  / /  | | | |_| | | | \  |  / /__  | |/   |/ /    | |_| | | | \ \  | | \ \
 * /_____/ |_| |_| /_/   |_| \_____/ |_|  \_| /_____| |___/|___/     \_____/ |_|  \_\ |_|  \_\
 * @author dengxiong90@foxmail.com
 * @param <T>
 * @since 2019.Q1
 *
 *
 */
public interface Edge<T> {
    public Vertex<T> getEndVertex();

    public void setEndVertex(Vertex<T> endVertex);

    public Double getWeight();

    public void setWeight(Double weight);
}
