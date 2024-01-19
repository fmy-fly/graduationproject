<template>
    <div class="container content-field">
        <div class="card">
            <div class="card-body">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script >

export default {
    name: 'ContentField',
}
</script>

<style scoped>
div.content-field {

    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
    margin-top: 250px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    max-width: 500px;
}
</style>