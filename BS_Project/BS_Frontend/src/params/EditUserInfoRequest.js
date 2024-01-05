// EditUserInfoRequest.js
export default class EditUserInfoRequest {
  constructor(userId, newUsername, newEmail, newPhone) {
    this.user_id = userId;
    this.new_username = newUsername;
    this.new_email = newEmail;
    this.new_phone = newPhone;
  }
}
