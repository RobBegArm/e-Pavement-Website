import classes from "./Header.module.css";

import HomeButton from "../navigation/HomeButton";
import MenuButton from "../navigation/MenuButton";
import HeaderNav from "../navigation/HeaderNav";

import { NavLink } from "react-router-dom";

const Header = (props) => {
  return (
    <header className={`${classes["header"]} flex--centered-vertically`}>
      <NavLink to="/">
        <HomeButton onClick={props.onHideMenu} />
      </NavLink>
      <HeaderNav />
      <MenuButton
        menuIsShown={props.menuIsShown}
        onShow={props.onShowMenu}
        onHide={props.onHideMenu}
      />
    </header>
  );
};

export default Header;
