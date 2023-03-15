import { useLocation } from "react-router-dom";

import classes from "./HeaderNav.module.css";
import HeaderNavLink from "./HeaderNavLink";

const HeaderNav = () => {
  const activePath = useLocation().pathname;
  return (
    <nav className={classes["header-nav"]}>
      <HeaderNavLink
        to="/about"
        label="About Us"
        className={activePath === "/about" ? "active" : ""}
        liKey={"header_link_about-us"}
      />
      <HeaderNavLink
        to="/product"
        label="Product"
        className={activePath === "/product" ? "active" : ""}
        liKey={"header_link_product"}
      />
      <HeaderNavLink
        to="/solutions"
        label="Solutions"
        className={activePath === "/solutions" ? "active" : ""}
        liKey={"header_link_solutions"}
      />
      <HeaderNavLink
        to="/contact"
        label="Contact"
        className={activePath === "/contact" ? "active" : ""}
        liKey={"header_link_contact"}
      />
    </nav>
  );
};

export default HeaderNav;
