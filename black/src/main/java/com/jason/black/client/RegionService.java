package com.jason.black.client;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

/**
 * Created by fuyongde on 2017/4/11.
 */
public class RegionService {
    public static final String REGION_URL = "http://localhost:8080/black/api/regions/";

    public static class Region {

        /**
         * id : 120000
         * parentId : 0
         * name : 天津市
         * level : 1
         * leaf : false
         * child : [{"id":120100,"parentId":120000,"name":"市辖区","level":2,"leaf":false},{"id":120200,"parentId":120000,"name":"县","level":2,"leaf":false}]
         */

        private int id;
        private int parentId;
        private String name;
        private int level;
        private boolean leaf;
        private List<ChildBean> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * id : 120100
             * parentId : 120000
             * name : 市辖区
             * level : 2
             * leaf : false
             */

            private int id;
            private int parentId;
            private String name;
            private int level;
            private boolean leaf;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public boolean isLeaf() {
                return leaf;
            }

            public void setLeaf(boolean leaf) {
                this.leaf = leaf;
            }

            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
            }
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }

    public interface Regions {
        @GET("{id}")
        Call<Region> getById(@Path("id") Integer id);
    }

    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REGION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Regions regions = retrofit.create(Regions.class);

        Call<Region> call = regions.getById(440000);
        Region region = call.execute().body();
        System.out.println(region);
    }
}
