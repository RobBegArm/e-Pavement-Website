import classes from "./MenuButton.module.css";

import { IoMenuSharp, IoCloseSharp } from "react-icons/io5";

const MenuButton = (props) => {
  const closeButton = (
    <button
      className={`${classes["menu-btn"]} flex--centered btn`}
      onClick={props.onHide}
      title={"Close"}
      id={"menu_btn"}
    >
      <div className={classes["menu-btn-icon-box"]}>
        <IoCloseSharp className={classes["icon"]} />
      </div>
    </button>
  );

  const menuButton = (
    <button
      className={`${classes["menu-btn"]} flex--centered btn`}
      onClick={props.onShow}
      title={"Menu"}
      id={"menu_btn"}
    >
      <div className={classes["menu-btn-icon-box"]}>
        <IoMenuSharp className={classes["icon"]} />
      </div>
    </button>
  );

  return props.menuIsShown ? closeButton : menuButton;
};

export default MenuButton;
