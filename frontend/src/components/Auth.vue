<template>
  <div id="allauth">
  <b-container>
    <b-row align-h="center" class="mt-5">
      <b-col cols="5">
        <b-card class="p-3">
        
          <h3 class="mb-4">Login</h3>
          <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group id="exampleInputGroup1"
                    label ="Email address:"
                    label-for="exampleInput1"
                    >
        <b-form-input id="exampleInput1"
                      type="email"
                      v-model="form.email"
                      required
                      placeholder="Enter email">
        </b-form-input>
      </b-form-group>
      <b-form-group id="exampleInputGroup2"
                    label="Password:"
                    label-for="exampleInput2">
        <b-form-input id="exampleInput2"
                      type="password"
                      v-model="form.password"
                      required
                      placeholder="Enter name">
        </b-form-input>
      </b-form-group>

      <b-form-group id="exampleGroup4">
        <b-form-checkbox-group v-model="form.checked" id="exampleChecks">
          <b-form-checkbox value="remember">Remember me</b-form-checkbox>
        </b-form-checkbox-group>
      </b-form-group>
      <div class="d-flex justify-content-between">
        <div>
          <b-button type="submit" variant="primary">Submit</b-button>&nbsp;
          <!--<b-button type="reset" variant="danger">Reset</b-button> -->
        </div>
        <div>
          <a v-bind:href="'/dashboard'" v-b-modal.modal1>Forgot Password</a>
        </div>
      </div>
    </b-form>
        </b-card>
      </b-col>
    </b-row>
  </b-container>

  <!--<b-modal id="modal1" title="Forgot Password">
      
    </b-modal>
  -->
  </div>
</template>

<script>

export default {
  name: 'allauth',
  components: {

  },
  data() {
    return {
      //showDismissibleAlert: false,
      form: {
        email: '',
        password: '',
        checked: []
      },
      show: true,
    }
  },
  methods: {
    handleClick() {
      this.showDismissibleAlert = true
    },
    onSubmit (evt) {
      evt.preventDefault();
      alert(JSON.stringify(this.form));
    },
    /* onReset (evt) {
      evt.preventDefault();
      // Reset our form values 
      this.form.email = '';
      this.form.password = '';
      this.form.food = null;
      this.form.checked = [];
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => { this.show = true });
    } */
  }
}
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Lato:400,700');
  body {
    background: #EEF1F4 !important;
    font-family: 'Lato', sans-serif !important;
  }
  .nav-background {
    background: #353535;
  }
</style>
