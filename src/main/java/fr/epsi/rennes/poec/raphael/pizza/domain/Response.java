package fr.epsi.rennes.poec.raphael.pizza.domain;

public class Response <T>{

    private T data;
    private boolean success = true;

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
