package com.stock.demo.common;


import java.util.Map;

public class JsonData {
    private Integer code;
    private Map<String,String> data;

    public JsonData() {
    }


    public Integer getCode() {
        return this.code;
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JsonData)) return false;
        final JsonData other = (JsonData) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JsonData;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "JsonData(code=" + this.getCode() + ", data=" + this.getData() + ")";
    }
}
